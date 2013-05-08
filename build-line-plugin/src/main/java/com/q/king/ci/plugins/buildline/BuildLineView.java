package com.q.king.ci.plugins.buildline;

import hudson.model.Descriptor;
import hudson.model.Item;
import hudson.model.TopLevelItem;
import hudson.model.View;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: kobeqing
 * Date: 13-5-7
 * Time: 下午9:59
 * To change this template use File | Settings | File Templates.
 */
public class BuildLineView extends View {

    protected BuildLineView(String name) {
        super(name);
    }

    @Override
    public Collection<TopLevelItem> getItems() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean contains(TopLevelItem topLevelItem) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onJobRenamed(Item item, String s, String s2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void submit(StaplerRequest staplerRequest) throws IOException, ServletException, Descriptor.FormException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Item doCreateItem(StaplerRequest staplerRequest, StaplerResponse staplerResponse) throws IOException, ServletException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
