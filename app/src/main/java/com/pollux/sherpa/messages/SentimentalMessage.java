package com.pollux.sherpa.messages;

/**
 * Created by Shabaz on 07-Feb-16.
 */
public class SentimentalMessage
{
    int pos,neg,neu;
    float posRatio,negRatio,neuRatio;
    String city;

    public SentimentalMessage(int pos, int neg, int neu,String city)
    {
        this.pos = pos;
        this.neg = neg;
        this.neu = neu;

        int total = pos+neg+neu;
        posRatio = (float)pos/(float)total;
        negRatio = (float)neg/(float)total;
        neuRatio = (float)neu/(float)total;
        this.city = city;
    }

    public String getCity()
    {
        return city;
    }

    public float getPosRatio()
    {
        return posRatio;
    }

    public float getNegRatio()
    {
        return negRatio;
    }

    public float getNeuRatio()
    {
        return neuRatio;
    }

    @Override
    public String toString()
    {
        return "SentimentalMessage{" +
                "pos=" + pos +
                ", neg=" + neg +
                ", neu=" + neu +
                ", posRatio=" + posRatio +
                ", negRatio=" + negRatio +
                ", neuRatio=" + neuRatio +
                ", city='" + city + '\'' +
                '}';
    }
}
