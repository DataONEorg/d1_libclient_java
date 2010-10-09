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

package org.dataone.service.mn;

import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.types.HeartbeatResponse;

/**
 * The DataONE MemberNode Health programmatic interface.  This defines an
 * implementation interface for Member Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface MemberNodeHealth 
{    
    public HeartbeatResponse heartbeat() throws NotImplemented;
    
    // Unclear whether these other methods are to be implemented, and if so,
    // how they differ from one another.
    //public PingResponse ping();
    //public StatusResponse getStatus(AuthToken token);
    //public void sohQuery(AuthToken token, String service);
}