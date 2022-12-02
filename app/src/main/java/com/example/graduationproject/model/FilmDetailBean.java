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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRuntimeMins() {
        return runtimeMins;
    }

    public void setRuntimeMins(String runtimeMins) {
        this.runtimeMins = runtimeMins;
    }

    public String getRuntimeStr() {
        return runtimeStr;
    }

    public void setRuntimeStr(String runtimeStr) {
        this.runtimeStr = runtimeStr;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPlotLocal() {
        return plotLocal;
    }

    public void setPlotLocal(String plotLocal) {
        this.plotLocal = plotLocal;
    }

    public boolean isPlotLocalIsRtl() {
        return plotLocalIsRtl;
    }

    public void setPlotLocalIsRtl(boolean plotLocalIsRtl) {
        this.plotLocalIsRtl = plotLocalIsRtl;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getDirectorList() {
        return directorList;
    }

    public void setDirectorList(String directorList) {
        this.directorList = directorList;
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers;
    }

    public String getWriterList() {
        return writerList;
    }

    public void setWriterList(String writerList) {
        this.writerList = writerList;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getStarList() {
        return starList;
    }

    public void setStarList(String starList) {
        this.starList = starList;
    }

    public String getActorList() {
        return actorList;
    }

    public void setActorList(String actorList) {
        this.actorList = actorList;
    }

    public String getFullCast() {
        return fullCast;
    }

    public void setFullCast(String fullCast) {
        this.fullCast = fullCast;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getGenreList() {
        return genreList;
    }

    public void setGenreList(String genreList) {
        this.genreList = genreList;
    }

    public String getCompanies() {
        return companies;
    }

    public void setCompanies(String companies) {
        this.companies = companies;
    }

    public String getCompanyList() {
        return companyList;
    }

    public void setCompanyList(String companyList) {
        this.companyList = companyList;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getCountryList() {
        return countryList;
    }

    public void setCountryList(String countryList) {
        this.countryList = countryList;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getLanguageList() {
        return languageList;
    }

    public void setLanguageList(String languageList) {
        this.languageList = languageList;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    public void setImDbRating(String imDbRating) {
        this.imDbRating = imDbRating;
    }

    public String getImDbRatingVotes() {
        return imDbRatingVotes;
    }

    public void setImDbRatingVotes(String imDbRatingVotes) {
        this.imDbRatingVotes = imDbRatingVotes;
    }

    public String getMetacriticRating() {
        return metacriticRating;
    }

    public void setMetacriticRating(String metacriticRating) {
        this.metacriticRating = metacriticRating;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public String getPosters() {
        return posters;
    }

    public void setPosters(String posters) {
        this.posters = posters;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(String keywordList) {
        this.keywordList = keywordList;
    }

    public String getSimilars() {
        return similars;
    }

    public void setSimilars(String similars) {
        this.similars = similars;
    }

    public String getTvSeriesInfo() {
        return tvSeriesInfo;
    }

    public void setTvSeriesInfo(String tvSeriesInfo) {
        this.tvSeriesInfo = tvSeriesInfo;
    }

    public String getTvEpisodeInfo() {
        return tvEpisodeInfo;
    }

    public void setTvEpisodeInfo(String tvEpisodeInfo) {
        this.tvEpisodeInfo = tvEpisodeInfo;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
