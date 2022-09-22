package com.example.graduationproject.model;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FilmDetailBean {
    private String id;
    private String title;
    private String originalTitle;
    private String fullTitle;
    private String type;
    private String year;
    private String image;
  //  private Date releaseDate;
    private String releaseDate;
    private String runtimeMins;
    private String runtimeStr;
    private String plot;
    private String plotLocal;
    private boolean plotLocalIsRtl;
    private String awards;
    private String directors;
   // private List<DirectorList> directorList;
    private String directorList;
    private String writers;
   // private List<WriterList> writerList;
    private String writerList;
    private String stars;
   // private List<StarList> starList;
   private String starList;
   // private List<ActorList> actorList;
   private String actorList;
    private String fullCast;
    private String genres;
   // private List<GenreList> genreList;
   private String genreList;
    private String companies;
  //  private List<CompanyList> companyList;
  private String companyList;
    private String countries;
   // private List<CountryList> countryList;
   private String countryList;
    private String languages;
   // private List<LanguageList> languageList;
   private String languageList;
    private String contentRating;
    private String imDbRating;
    private String imDbRatingVotes;
    private String metacriticRating;
    private String ratings;
    private String wikipedia;
    private String posters;
    private String images;
    private String trailer;
   // private BoxOffice boxOffice;
    private String boxOffice;
    private String tagline;
    private String keywords;
   // private List<String> keywordList;
   private String keywordList;
   // private List<Similars> similars;
   private String similars;
    private String tvSeriesInfo;
    private String tvEpisodeInfo;
    private String errorMessage;

}
