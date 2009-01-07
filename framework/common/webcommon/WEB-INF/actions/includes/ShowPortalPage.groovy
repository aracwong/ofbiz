/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.ofbiz.entity.*;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.condition.*;

portalPageId = parameters.portalPageId;
if(!portalPageId){
	portalPageId = parameters.parentPortalPageId;
}

portalPage = delegator.findByPrimaryKey("PortalPage", [portalPageId : portalPageId]);
// check if the user created a private page for a system page
if (portalPage.ownerUserLoginId.equals("_NA_")) {
	portalPages = delegator.findByAnd("PortalPage", [originalPortalPageId : portalPageId, ownerUserLoginId : parameters.userLogin.userLoginId]);
	if (UtilValidate.isNotEmpty(portalPages)) {
		portalPage = portalPages.get(0);
	}
}
parameters.portalPageId = portalPage.portalPageId;
context.portalPage = portalPage;
context.portalPageColumns = portalPage.getRelated("PortalPageColumn");
context.portalPagePortlets = delegator.findByAnd("PortalPagePortletView", [portalPageId : portalPage.portalPageId]);

