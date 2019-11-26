package video.api.java.sdk.infrastructure.pagination;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.pagination.Page;

public class PageIterator<T> implements java.util.Iterator<T> {

    private PageLoader<T>         pageLoader;
    private java.util.Iterator<T> current;
    private int                   pagesTotal;
    private QueryParams           queryParams;


    public PageIterator(PageLoader<T> pageLoader, QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        this.pageLoader  = pageLoader;
        this.queryParams = new QueryParams(queryParams);
        this.current     = this.loadPage();
    }

    @Override
    public boolean hasNext() {

        if (this.current.hasNext()) {

            return true;
        } else {
            if (this.getCurrentPage() < pagesTotal) {
                this.incrementNumberPage();
                try {
                    this.current = this.loadPage();
                } catch (ResponseException | IllegalArgumentException e) {
                    return false;
                }
                return true;
            }
            else {
                return false;
            }
        }
    }

    @Override
    public T next() {
        return this.current.next();
    }

    private java.util.Iterator<T> loadPage() throws ResponseException, IllegalArgumentException {
        Page<T> page = pageLoader.load(this.queryParams);

        pagesTotal = page.total;

        return page.items.iterator();
    }


    private void incrementNumberPage() {

        this.queryParams.currentPage++;
    }

    private int getCurrentPage() {
        return this.queryParams.currentPage;
    }


}