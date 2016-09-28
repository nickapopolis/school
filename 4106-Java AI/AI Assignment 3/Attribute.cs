using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;

namespace Classification
{
    class Attribute
    {
        String[] values;
        String name;

        public Attribute(String n,  String[] v)
        {
            name = n;
            values = v;
        }
        public String getName()
        {
            return name;
        }
        public int numValues()
        {
            return values.Length;
        }
        public String getValue(int index)
        {
            if (index < values.Length && index >= 0)
            {
                return values[index];
            }
            else
                return null;
        }
        public String[] getValues()
        {
            return values;
        }
        public String toString()
        {
            String ret = "Attribute name: " + name + "\n";
            
            foreach (String val in values)
            {
                ret += val + "\n";
            }
            return ret;
        }
    }
}
