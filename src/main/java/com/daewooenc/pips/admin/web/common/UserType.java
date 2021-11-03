package com.daewooenc.pips.admin.web.common;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-11-12      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-11-12
 **/
public enum UserType {
    SYSTEM("SYSTEM_ADMIN"), SUB_SYSTEM("SUB_SYSTEM_ADMIN"), COMPLEX("COMPLEX_ADMIN"), COMPLEX_GROUP("COMPLEX_GROUP_ADMIN"), MULTI_COMPLEX("MULTI_COMPLEX_ADMIN") ;

    final private String groupName;

    UserType(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}