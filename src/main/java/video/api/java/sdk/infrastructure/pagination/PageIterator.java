package video.api.java.sdk.infrastructure.pagination;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;

import java.util.Iterator;

public class PageIterator<T> implements Iterator<T> {

    private PageLoader<T> pageLoader;
    private Iterator<T>   current;
    private int           pagesTotal;
    private QueryParams   queryParams;


    public PageIterator(PageLoader<T> pageLoader, QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        this.pageLoader  = pageLoader;
        this.queryParams = new QueryParams(queryParams);
        this.current     = this.loadPage();
    }

    @Override
    public boolean hasNext() {

        if (this.current.hasNext()) {
            System.out.println("etape 1");
            return true;
        } else {
            // A. Si current n'a pas de hasNext(), mais qu'il y a d'autres pages
            if (this.getCurrentPage() < pagesTotal) {
                System.out.println("etape 2");

                this.incrementNumberPage();
                try {
                    this.current = this.loadPage();
                } catch (ResponseException | IllegalArgumentException e) {
                    System.out.println("Players : Failed loading page " + this.getCurrentPage() + "pageSize ");
                    return false;
                }
                return true;
            }
            // cas particulier : on est à la dernière page, il n'y a plus d'éléments, on renvoie false
            else {
                System.out.println("etape 3");
                return false;
            }
        }
    }

    @Override
    public T next() {
        return this.current.next();
    }

    private Iterator<T> loadPage() throws ResponseException, IllegalArgumentException {


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