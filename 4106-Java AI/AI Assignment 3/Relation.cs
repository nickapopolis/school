using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using MathNet.Numerics.LinearAlgebra;

namespace Classification
{

    class Relation
    {
        protected String relationName;
        protected List<Attribute> attributes;
        protected List<String[]> data;

        public Relation(String relName)
        {
            relationName = relName;
            attributes = new List<Attribute>();
            data = new List<String[]>();
        }
        public void addAttribute(Attribute att)
        {
            attributes.Add(att);
        }
        public void addDataSet(String[] set)
        {
            data.Add(set);
        }
        public void calculateDecisionTree()
        {
            EntropyValue expectedGain = new EntropyValue();

            foreach (String[] str in data)
            {
                if(isPositive(str[attributes.Count - 1]))
                    expectedGain.incrementPositive();
                else
                    expectedGain.incrementNegative();
            }
            //calculate the expected entropy of the data
            double entropy = expectedGain.calculateEntropy();

            //calculate entropy of all attributes
            List<InfoGain> attributeGain = new List<InfoGain>();
            for (int i = 0; i < attributes.Count -1; i++)
            {
                InfoGain gain = new InfoGain(attributes[i], entropy);
                attributeGain.Add(gain);
                for (int j = 0; j < data.Count; j++)
                {
                    String[] dataSet = data[j];
                    String value = dataSet[i];
                    bool expected = isPositive(dataSet[attributes.Count-1]);
                    gain.increment(value, expected);
                }
                gain.calculateInformationGain();
            }
            double bestPredictorValue = attributeGain.Max(a => a.getInformationGain());
            InfoGain bestPredictor = null;
            foreach (InfoGain gain in attributeGain)
            {
                Console.Out.WriteLine(gain.getName() +": " +gain.getInformationGain());
                if (gain.getInformationGain() == bestPredictorValue)
                {
                    bestPredictor = gain;
                }
            }
            Console.Out.WriteLine("best predictor: " + bestPredictor.getName());

        }
        public void calculateNaiveBayes()
        {
            calculateClassification(false);
        }
        public void calculateOptimalBayes()
        {
            calculateClassification(true);
        }
        public void calculateClassification(bool isOptimal)
        {
            Vector[] vectors = new Vector[data.Count];

            for (int i=0; i< data.Count; i++)
            {
                String[] dataset = data[i];
                double[] numValues = new double[dataset.Length];
                //convert data to numbers
                for (int j = 0; j < numValues.Length; j++)
                {
                    if (dataset[j] == "?")
                    {
                        numValues[j] = 0;
                    }
                    else
                    {
                        numValues[j] = Double.Parse(dataset[j]);
                    }
                }
                //add numbers to a vector
                Vector dataVector = new Vector(numValues);
                vectors[i] = dataVector;
            }
            List<Vector> trainingSet = new List<Vector>();
            List<Vector> testingSet = new List<Vector>();

            int trainingSetLength = (int)vectors.Length/2;
            //add first half of vectors to training set
            for (int i = 0; i < trainingSetLength; i++)
            {
                trainingSet.Add(vectors[i]);
            }
            //add second half of vectors to testing set
            for (int i = trainingSetLength; i < vectors.Length; i++)
            {
                testingSet.Add(vectors[i]);
            }

            //calculate mean of vectors
            Vector trainingMean = calculateMean(trainingSet);
            Vector testingMean = calculateMean(testingSet);

            Matrix trainingMeanMatrix = trainingMean.ToColumnMatrix();
            Matrix testingMeanMatrix = testingMean.ToColumnMatrix();

            Matrix trainingMeanTranspose = trainingMeanMatrix.Clone();
            trainingMeanTranspose.Transpose();
            Matrix testingMeanTranspose = testingMeanMatrix.Clone();
            testingMeanTranspose.Transpose();

            Matrix trainingCovariance = Matrix.Zeros(testingMean.Length);
            Matrix testingCovariance = Matrix.Zeros(testingMean.Length);

            int dimension = trainingMean.Length;
            //if isoptimal is true calculates the covariance matrix with optimal bayes, otherwise it uses naive bayes
            if (isOptimal)
            {
                
                //optimal bayes
                for (int i = 0; i < dimension; i++)
                {
                    for (int j = 0; j < dimension; j++)
                    {
                        double sum = 0;
                        foreach (Vector v in trainingSet)
                        {
                            sum += Math.Pow((v[i] - trainingMean[i]) * (v[j] - trainingMean[j]), 2);
                        }
                        double expectedValue = sum / trainingSet.Count;
                        trainingCovariance[i, j] = expectedValue;
                    }
                }
                for (int i = 0; i < dimension; i++)
                {
                    for (int j = 0; j < dimension; j++)
                    {
                        double sum = 0;
                        foreach (Vector v in testingSet)
                        {
                            sum += Math.Pow((v[i] - testingMean[i]) * (v[j] - testingMean[j]), 2);
                        }
                        double expectedValue = sum / testingSet.Count;
                        testingCovariance[i, j] = expectedValue;
                    }
                }
            }
            else
            {
                //naive bayes
                for (int i = 0; i < dimension; i++)
                {
                    double sum = 0;
                    foreach (Vector v in trainingSet)
                    {
                        sum += Math.Pow(v[i] - trainingMean[i], 2);
                    }
                    double expectedValue = sum / trainingSet.Count;
                    trainingCovariance[i, i] = expectedValue;
                }
                for (int i = 0; i < dimension; i++)
                {
                    double sum = 0;
                    foreach (Vector v in testingSet)
                    {
                        sum += Math.Pow(v[i] - testingMean[i], 2);
                    }
                    double expectedValue = sum / testingSet.Count;
                    testingCovariance[i, i] = expectedValue;
                }
            }

            
            //calculate a
            Matrix a = inverse(trainingCovariance) - inverse(testingCovariance);

            //calculate b
            Matrix b = testingMeanTranspose.Multiply(inverse(testingCovariance));
            Matrix bTrain = trainingMeanTranspose.Multiply(inverse(trainingCovariance));
            b.Subtract(bTrain);
            b.Add(b);

            //calculate c
            Matrix cTrain = trainingMeanTranspose.Multiply(inverse(trainingCovariance)).Multiply(trainingMeanMatrix);
            Matrix cTest = testingMeanTranspose.Multiply(inverse(testingCovariance)).Multiply(testingMeanMatrix);
            cTrain.Subtract(cTest);
            
            double lnTrain = 1.0/ Math.Log(Math.E, trainingCovariance.Determinant());
            double lnTest  = 1.0/ Math.Log(Math.E, testingCovariance.Determinant());
            double cSum = -lnTrain -lnTest;

            double c = cTrain.CopyToArray()[0, 0];
            c += cSum;

            Matrix bt = b.Clone();

            foreach (Vector v in testingSet)
            {
                Matrix x = v.ToColumnMatrix();
                Matrix xt = x.Clone();
                xt.Transpose();

                Matrix A = xt.Multiply(a).Multiply(x);
                Matrix B = bt.Multiply(x);

                double classification = A[0, 0] + B[0, 0] + c;
                Console.Out.WriteLine("classification: "+classification);
            }
        }
        public int getNumAttributes()
        {
            return attributes.Count;
        }
        public String toString()
        {
            String str = "Relation name: " + relationName + "\n";
            foreach (Attribute att in attributes)
            {
                str += att.toString();
            }
            str += "Data:\n";
            foreach (String[] s in data)
            {
                str += String.Concat(s) + "\n";
            }
            return str;
        }
        public bool isPositive(String str)
        {
            return str.Equals("yes");
        }
        public Vector calculateMean(List<Vector> vectors)
        {
            Vector vectorSum = new Vector(vectors[0].Length);
            foreach (Vector v in vectors)
            {
                vectorSum.AddInplace(v);
            }
            //calculate mean of vectors
            double[] denominator = new double[vectors[0].Length];
            for (int i = 0; i < denominator.Length; i++)
            {
                denominator[i] = vectors[0].Length;
            }
            Vector den = new Vector(denominator);
            Vector vectorMean = vectorSum.ArrayDivide(den);

            return vectorMean;
        }
        public Matrix inverse(Matrix m)
        {
            double det = m.Determinant();
            if (det == 0)
            {
                Matrix newm = Matrix.Identity(m.RowCount, m.ColumnCount);
                return newm.Inverse();
            }
            return m.Inverse();
        }
        
    }


}
