package com.daewooenc.pips.admin.core.domain.authorization;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 사용자 그룹 Domain.
 */
@XStreamAlias("userGroup")
public class UserGroup {

	/** The userGroupId. */
	private String userGroupId;

	/** The userGroupName. */
	@NotEmpty
	private String userGroupName;

	/** The userGroupLevel. */
	@NotEmpty
	private String userGroupLevel;

	/** The description. */
	private String description;

	/** UserGroupAuth string data. */
	private String userGroupAuth;

	/** The type. */
	private String type;

	/**
	 * Gets the userGroupId.
	 *
	 * @return the userGroupId
	 */
	public String getUserGroupId() {
		return userGroupId;
	}

	/**
	 * Sets the userGroupId.
	 *
	 * @param userGroupId the new userGroupId
	 */
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	/**
	 * Gets the userGroupName.
	 *
	 * @return the userGroupName
	 */
	public String getUserGroupName() {
		return userGroupName;
	}

	/**
	 * Sets the userGroupName.
	 *
	 * @param userGroupName the new userGroupName
	 */
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	/**
	 * Gets the userGroupLevel.
	 *
	 * @return the userGroupLevel
	 */
	public String getUserGroupLevel() {
		return userGroupLevel;
	}

	/**
	 * Sets the userGroupLevel.
	 *
	 * @param userGroupLevel the new userGroupLevel
	 */
	public void setUserGroupLevel(String userGroupLevel) {
		this.userGroupLevel = userGroupLevel;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the user group auth.
	 *
	 * @return the user group auth
	 */
	public String getUserGroupAuth() {
		return userGroupAuth;
	}

	/**
	 * Sets the user group auth.
	 *
	 * @param userGroupAuth the new user group auth
	 */
	public void setUserGroupAuth(String userGroupAuth) {
		this.userGroupAuth = userGroupAuth;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

}
