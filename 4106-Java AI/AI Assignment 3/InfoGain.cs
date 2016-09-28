using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
namespace Classification
{
    class InfoGain
    {
        String gainName;
        Hashtable values;
        double expectedGain = 0;
        double entropy = 0;

        public InfoGain(Attribute att, double gain)
        {
            gainName = att.getName();
            expectedGain = gain;
            values = new Hashtable();
            addEntropyValues(att);
        }
        public String getName()
        {
            return gainName;
        }
        public void addEntropyValues(Attribute att)
        {
            foreach (String value in att.getValues())
            {
                addEntropyValue(value);
            }
        }
        public void addEntropyValue(String name)
        {
            EntropyValue value = new EntropyValue();
            values.Add(name, value);
        }
        public void increment(String name, bool positive)
        {
            EntropyValue value = null;

            value = (EntropyValue)values[name];
            if (positive)
                value.incrementPositive();
            else
                value.incrementNegative();
        }
        public double calculateInformationGain()
        {
            double totalValues = 0;
            double ent = expectedGain;

            ICollection c = values.Keys;
            foreach (string str in c)
            {
                EntropyValue value = (EntropyValue)values[str];
                totalValues += value.getNumValues();
            }
            foreach (string str in c)
            {
                EntropyValue value = (EntropyValue)values[str];
                ent -= (value.getNumValues() / totalValues) * value.calculateEntropy();
            }
            entropy = ent;
            return ent;
        }
        public double getInformationGain()
        {
            return entropy;
        }
    }
}
