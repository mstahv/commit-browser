package org.commitbrowser;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import javax.inject.Inject;
import org.vaadin.viritin.LazyList;
import org.vaadin.viritin.ListContainer;

/**
 *
 */
@Theme("valo")
@CDIUI("")
public class MyUI extends UI {
    
    @Inject
    GitRepositoryService gitRepositoryService;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Grid grid = new Grid();

        LazyList<Commit> lazyList = new LazyList<>(
                firstRow -> gitRepositoryService.find(firstRow,
                        LazyList.DEFAULT_PAGE_SIZE), gitRepositoryService::count);
        grid.setContainerDataSource(new ListContainer(lazyList));

        grid.setSizeFull();

        setContent(grid);

    }
}
