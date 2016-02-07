package com.pollux.sherpa.messages;

/**
 * Created by Shabaz on 07-Feb-16.
 */
public class SentimentalMessage
{
    int pos,neg,neu;
    float posRatio,negRatio,neuRatio;

    public SentimentalMessage(int pos, int neg, int neu)
    {
        this.pos = pos;
        this.neg = neg;
        this.neu = neu;

        int total = pos+neg+neu;
        posRatio = (float)pos/(float)total;
        negRatio = (float)neg/(float)total;
        neuRatio = (float)neu/(float)total;
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
}
