package com.pollux.sherpa.model;

/**
 * Created by idea on 07-02-2016.
 */
public class NearbyPlacesResponse {

    private Results[] results;

    public Results[] getResults ()
    {
        return results;
    }

    public void setResults (Results[] results)
    {
        this.results = results;
    }

    public class Results
    {
        private Photos[] photos;

        private String name;

        private String rating;

        private String[] types;

        public Photos[] getPhotos ()
        {
            return photos;
        }

        public void setPhotos (Photos[] photos)
        {
            this.photos = photos;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getRating ()
        {
            return rating;
        }

        public void setRating (String rating)
        {
            this.rating = rating;
        }

        public String[] getTypes ()
        {
            return types;
        }

        public void setTypes (String[] types)
        {
            this.types = types;
        }

    }

    public class Photos
    {
        private String[] html_attributions;

        public String[] getHtml_attributions ()
        {
            return html_attributions;
        }

        public void setHtml_attributions (String[] html_attributions)
        {
            this.html_attributions = html_attributions;
        }

    }
}
