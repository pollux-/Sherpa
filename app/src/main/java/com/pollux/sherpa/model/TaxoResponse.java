package com.pollux.sherpa.model;

/**
 * Created by idea on 07-02-2016.
 */
public class TaxoResponse {

    private Taxonomy[] taxonomy;

    public Taxonomy[] getTaxonomy ()
    {
        return taxonomy;
    }

    public void setTaxonomy (Taxonomy[] taxonomy)
    {
        this.taxonomy = taxonomy;
    }

    public class Taxonomy
    {
        private String score;

        private String label;

        public String getScore ()
        {
            return score;
        }

        public void setScore (String score)
        {
            this.score = score;
        }

        public String getLabel ()
        {
            return label;
        }

        public void setLabel (String label)
        {
            this.label = label;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [score = "+score+", label = "+label+"]";
        }
    }
}
