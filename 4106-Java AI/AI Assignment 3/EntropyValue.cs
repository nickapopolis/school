using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Classification
{
    class EntropyValue
    {
        int positive = 0;
        int negative = 0;
        InfoGain child = null;

        public void incrementPositive()
        {
            positive++;
        }
        public void incrementNegative()
        {
            negative++;
        }
        public double calculateEntropy()
        {
            double pos = (double)positive;
            double neg = (double)negative;
            double total = pos + neg;

            if (pos == 0 || neg == 0)
            {
                return 0;
            }
            double positiveEntropy = -(pos / total) * Math.Log(pos / total, 2);
            double negativeEntropy = -(neg / total) * Math.Log(neg / total, 2);
            double entropy = positiveEntropy + negativeEntropy;

            return entropy;
        }
        public double getNumValues()
        {
            return positive + negative;
        }
        public void setChild(InfoGain gain)
        {
            child = gain;
        }
        public InfoGain getChild()
        {
            return child;
        }
        
    }
}
