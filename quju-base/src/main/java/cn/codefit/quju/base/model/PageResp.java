package cn.codefit.quju.base.model;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;


@Data
public class PageResp<T> {

    private Page<T> page;

    public static <T> PageResp<T> create(List<T> result, int count, int pageNo, int pageSize) {
        PageResp<T> pageResponse = new PageResp<>();
        Page<T> page = new Page<T>();
        page.setTotalCount(count);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setHasNext(count > (pageNo * pageSize));
        page.setResult((result == null) ? Collections.emptyList() : result);
        pageResponse.setPage(page);
        return pageResponse;
    }

    public static <T> Page<T> createPage(List<T> result, int count, int pageNo, int pageSize) {
        if (CollectionUtils.isEmpty(result)) {
            return createEmptyPage(pageNo, pageSize);
        }

        return create(result, count, pageNo, pageSize).getPage();
    }

    public static <T> Page<T> createEmptyPage(int pageNo, int pageSize) {
        Page<T> page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setResult(Collections.emptyList());
        page.setTotalCount(0);
        return page;
    }

    public static boolean paging(Integer pageNum, Integer pageSize, int count) {
        if ((count % pageSize == 0 && pageNum > count / pageSize) || pageNum - 1 > count / pageSize) {
            return true;
        }
        return false;
    }

    public static <T> List<T> manualPaging(List<T> result, Integer pageNum, Integer pageSize) {
        Integer size = result.size();
        Integer count = size / pageSize;
        Integer fromIndex = pageSize * (pageNum - 1);
        int toIndex = fromIndex + pageSize;
        if (toIndex >= size) {
            toIndex = size;
        }
        if (pageNum > count + 1) {
            fromIndex = 0;
            toIndex = 0;
        }
        return result.subList(fromIndex, toIndex);
    }

    @Data
    public static class Page<T> {
        private int totalCount;
        private int pageNo;
        private int pageSize;
        private boolean hasNext;
        private List<T> result;
    }
}
