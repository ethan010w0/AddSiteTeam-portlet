package view.action;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.portal.WebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

@ManagedBean(name = "AddSiteTeamAction")
@ViewScoped
public class AddSiteTeamAction {
  private String name;

  // Add site team.
  public void add() {
    // Get current user id now.
    User user = LiferayFacesContext.getInstance().getUser();
    long userId = user.getUserId();

    // Get current group id now.
    FacesContext facesContext = FacesContext.getCurrentInstance();
    PortletRequest portletRequest = (PortletRequest) facesContext.getExternalContext().getRequest();
    ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
    long groupId = themeDisplay.getLayout().getGroupId();

    // Add team.
    try {
      Team team = TeamLocalServiceUtil.addTeam(userId, groupId, name, null);
    } catch (Exception e) {
      FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.toString());
      facesContext.addMessage(null, facesMessage);
      return;
    }

    // Show message.
    FacesMessage facesMessage =
        new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Add Team: " + name);
    facesContext.addMessage(null, facesMessage);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
