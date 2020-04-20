package com.apple.assignment.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;



@Component(name="Apple Tag Search Service",service = TagSearchService.class, immediate=true)
public class TagSearchServiceImpl implements TagSearchService {

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Override
	public List<String> getTagList(String[] tagLists) {
		ResourceResolver resolver;
		Map<String, Object> resourceResolverAuthentication = new HashMap<String, Object>();
		resourceResolverAuthentication.put(ResourceResolverFactory.SUBSERVICE, "getresourceresolver");
		List<String> pageList = new ArrayList<String>();
		try {
			resolver = resourceResolverFactory.getServiceResourceResolver(resourceResolverAuthentication);

			String queryTagList="";
			for(String tagList:tagLists) {
				queryTagList += " or  s.[cq:tags] = '"+tagList+"'";
			}
			
			String queryPath = "";
			Session session = resolver.adaptTo(Session.class);
			QueryManager queryManager = session.getWorkspace().getQueryManager();
			Query query = queryManager.createQuery(
					"SELECT s.[cq:tags] FROM [nt:base] AS s WHERE ISDESCENDANTNODE([/content/we-retail/us/en]) and "+queryTagList.replaceFirst(" or ", "")+"ORDER BY s.[cq:tags] DESC",
					Query.JCR_SQL2);
			final NodeIterator nodeIterator = query.execute().getNodes();

			if (nodeIterator != null) {
				while (nodeIterator.hasNext()) {
					Node queryNode = nodeIterator.nextNode();
					queryPath = queryNode.getPath();
					pageList.add(queryPath);
				}
			}
			
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		
		return pageList;

	}

}
