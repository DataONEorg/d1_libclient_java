/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dataone.service.cn;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;

import org.dataone.service.types.Session;
import org.dataone.service.types.Identifier;
import org.dataone.service.types.Subject;
import org.dataone.service.types.Event;
import org.dataone.service.types.AccessPolicy;

/**
 * The DataONE CoordinatingNode Tier2 Authorization interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface CNAuthorization {

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNAuthorization.setOwner
     */
    public Identifier setOwner(Session session, Identifier pid, Subject userId)
        throws InvalidToken, ServiceFailure, NotFound, NotAuthorized, 
        NotImplemented, InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNAuthorization.isAuthorized
     */
    public boolean isAuthorized(Session session, Identifier pid, Event operation)
        throws ServiceFailure, InvalidToken, NotFound, NotAuthorized, 
        NotImplemented, InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNAuthorization.setAccess
     */
    public boolean setAccess(Session session, Identifier pid, AccessPolicy accessPolicy)
        throws InvalidToken, ServiceFailure, NotFound, NotAuthorized, 
        NotImplemented, InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNAuthorization.setAccessPolicy
     */
    public boolean setAccessPolicy(Session session, Identifier pid, 
        AccessPolicy policy) throws InvalidToken, NotFound, NotImplemented, 
        NotAuthorized, ServiceFailure, InvalidRequest;
}
