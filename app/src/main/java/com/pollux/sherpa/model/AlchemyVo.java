package com.pollux.sherpa.model;

/**
 * Created by idea on 07-02-2016.
 */
public class AlchemyVo
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
}
