package com.pollux.sherpa.model;

/**
 * Created by idea on 07-02-2016.
 */
public class AlchemyResponse
{
    private String status;

    private String language;

    private Entities[] entities;

    private String url;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getLanguage ()
    {
        return language;
    }

    public void setLanguage (String language)
    {
        this.language = language;
    }

    public Entities[] getEntities ()
    {
        return entities;
    }

    public void setEntities (Entities[] entities)
    {
        this.entities = entities;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", language = "+language+", entities = "+entities+", url = "+url+"]";
    }

    public class Entities
    {
        private String text;

        private String count;

        private String relevance;

        private String type;

        public String getText ()
        {
            return text;
        }

        public void setText (String text)
        {
            this.text = text;
        }

        public String getCount ()
        {
            return count;
        }

        public void setCount (String count)
        {
            this.count = count;
        }

        public String getRelevance ()
        {
            return relevance;
        }

        public void setRelevance (String relevance)
        {
            this.relevance = relevance;
        }

        public String getType ()
        {
            return type;
        }

        public void setType (String type)
        {
            this.type = type;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [text = "+text+", count = "+count+", relevance = "+relevance+", type = "+type+"]";
        }
    }
}
