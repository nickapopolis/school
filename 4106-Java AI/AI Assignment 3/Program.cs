using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MathNet.Numerics.LinearAlgebra;
using System.IO;
using System.Text.RegularExpressions;
namespace Classification
{
    
    class Program
    {
        public void begin()
        {
            String input;
            bool cont = true;
            while(cont)
            {
                Relation data = null;
                Console.Out.WriteLine("    Options");
                Console.Out.WriteLine("1 - Decision Tree");
                Console.Out.WriteLine("2 - Naive Bayes");
                Console.Out.WriteLine("3 - Optimal Bayes");
                Console.Out.Write("Any other key to exit: ");
                input = Console.In.ReadLine();
                
                switch(Convert.ToInt32(input.Trim()))
                {
                    case 1:
                        data = readDecisionTreeData("data_nominal.txt");
                        data.calculateDecisionTree();
                        break;
                    case 2:
                        data = readClassificationData("breast-cancer-data.txt");
                        data.calculateNaiveBayes();
                        break;
                    case 3:
                        data = readClassificationData("breast-cancer-data.txt");
                        data.calculateOptimalBayes();
                        break;
                    default:
                        cont = false;
                        break;
                }
            }
                    
        }
        public Relation readDecisionTreeData(String fileName)
        {
            TextReader rdr = new StreamReader(fileName);
            Relation relation = null;
            String s;
            
            while ((s = rdr.ReadLine()) != null)
            {
                if (s.Length <=0 ||s[0] == '%' || s[0] == '#')
                {
                    continue;
                }
                else if (s[0] == '@')
                {
                    String[] args = s.Substring(1, s.Length - 1).Split(' ');
                    if (args[0].Equals("relation"))
                    {
                        relation = new Relation(args[1]);
                    }
                    else if (args[0].Equals("attribute"))
                    {
                        String attributes = "";
                        for (int i = 2; i < args.Length; i++)
                        {
                            attributes += args[i];
                        }
                        attributes = attributes.Substring(1, attributes.Length - 2);
                        String[] splitAttributes = attributes.Split(',');
                        
                        Attribute attribute = new Attribute(args[1], splitAttributes);
                        relation.addAttribute(attribute);
                    }
                    else if (args[0].Equals("data"))
                    {
                        String currentLine;
                        while((currentLine = rdr.ReadLine()) != null)
                        {
                            currentLine = currentLine.Trim();
                            String[] attributeValues = currentLine.Split(',');
                            if(attributeValues.Length != relation.getNumAttributes())
                            {
                                break;
                            }
                            else
                            {
                                relation.addDataSet(attributeValues);
                            }
                        }
                    }
                }
            }
            return relation;
        }
        public Relation readClassificationData(String fileName)
        {
            Relation relation = new Relation(fileName);
            TextReader rdr = new StreamReader(fileName);
            String s;

            while ((s = rdr.ReadLine()) != null)
            {
                if (s.Length <= 0 || s[0] == '%' || s[0] == '#')
                {
                    continue;
                }
                else
                {
                    s = s.Trim();
                    String[] attributeValues = s.Split(',');
                    relation.addDataSet(attributeValues);
                }
            }
            return relation;
        }
        static void Main(string[] args)
        {
            Program p = new Program();
            p.begin();
        }
        
    }
    

}
