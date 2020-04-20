package com.apple.assignment.core.models;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.osgi.service.component.annotations.Reference;

import com.apple.assignment.core.service.impl.TagSearchService;


@Model(adaptables = Resource.class)
public class TagSearchModel {

	private String[] tagPageList;

	private List<String> pageList;

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Inject
	private TagSearchService tagSearchService;

	@Inject
	public TagSearchModel(@Optional @Named("cq:tags") String[] tagPageList) {
		this.tagPageList = tagPageList;
	}

	public String[] getTagPageList() {
		return tagPageList;
	}

	@PostConstruct
	protected void init() {
		if (tagPageList != null) {
			pageList = tagSearchService.getTagList(tagPageList);
		}
	}

	public List<String> getPageList() {
		return pageList;
	}
}
