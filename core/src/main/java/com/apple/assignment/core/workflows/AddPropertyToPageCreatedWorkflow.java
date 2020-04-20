package com.apple.assignment.core.workflows;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.commons.jcr.JcrConstants;

/**
 * 
 * Workflow to add property to page content
 * @author siddu
 *
 */
@Component(service=WorkflowProcess.class, property = {"process.label=Adding property to page content"})
public class AddPropertyToPageCreatedWorkflow implements WorkflowProcess {

	private static final String TYPE_JCR_PATH = "JCR_PATH";

	public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) throws WorkflowException {
		
		WorkflowData workflowData = item.getWorkflowData();
		
		if (workflowData.getPayloadType().equals(TYPE_JCR_PATH)) {
			
			String path = workflowData.getPayload().toString() + "/jcr:content";
			
			try {
				
				Session jcrSession = session.adaptTo(Session.class);

				Node root = jcrSession.getRootNode();

				Node node = root.getNode(path.replaceFirst("/", ""));

				if (node != null) {
					node.addMixin(JcrConstants.MIX_VERSIONABLE);
					node.setProperty("AppleInc", true);
					jcrSession.save();
					jcrSession.logout();
				}
			} catch (RepositoryException e) {
				throw new WorkflowException(e.getMessage(), e);
			}
		}
	}
}