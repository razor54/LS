package pt.isel.ls.view.htmlViews;

import pt.isel.ls.model.data.entities.NoEntity;

import java.util.stream.Stream;


public class IndexView extends View<NoEntity> {
    @Override
    public String getHtml(Stream<NoEntity> entities) {
        return null;
    }



    @Override
    protected String getEntityHtml(NoEntity course) {
        return null;
    }
}
