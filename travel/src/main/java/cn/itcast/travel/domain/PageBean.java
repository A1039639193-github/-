package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
    private int totalPage; //总页码
    private int totalCount; //总记录条数
    private int currentPage; //当前页码
    private int pageSizes ; //每页记录数
    private List<T> list; //每页展示的记录集合

    public PageBean() {
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSizes() {
        return pageSizes;
    }

    public void setPageSizes(int pageSizes) {
        this.pageSizes = pageSizes;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalPage=" + totalPage +
                ", totalCount=" + totalCount +
                ", currentPage=" + currentPage +
                ", pageSizes=" + pageSizes +
                ", list=" + list +
                '}';
    }
}
