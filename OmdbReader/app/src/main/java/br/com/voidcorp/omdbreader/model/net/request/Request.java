package br.com.voidcorp.omdbreader.model.net.request;

public class Request {

    public static final String SHORT_PLOT = "short";
    public static final String FULL_PLOT = "full";

    private String mId;

    private String mSearch;

    private int mPage;

    private String mPlot;

    private String mResponseType;

    public Request() {
        mResponseType = "json";
    }

    public Request(final String searchText, final int page) {
        this();
        mPlot = SHORT_PLOT;
        mSearch = searchText;
        mPage = page;
    }

    public Request(final String id) {
        this();
        mPlot = FULL_PLOT;
        mId = id;
    }

    public String getResponseType() {
        return mResponseType;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getSearch() {
        return mSearch;
    }

    public void setSearch(String search) {
        this.mSearch = search;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        this.mPage = page;
    }

    public String getPlot() {
        return mPlot;
    }

    public void setPlot(String plot) {
        this.mPlot = plot;
    }
}
