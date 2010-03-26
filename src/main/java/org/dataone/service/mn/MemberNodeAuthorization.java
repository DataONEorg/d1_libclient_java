package org.dataone.service.mn;

import org.dataone.service.exceptions.AuthenticationTimeout;
import org.dataone.service.exceptions.InvalidCredentials;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.IdentifierType;

/**
 * The DataONE MemberNode Authorization programmatic interface.  This defines an
 * implementation interface for Member Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface MemberNodeAuthorization 
{
    public AuthToken login(String user, String password);
    public void logout(AuthToken token) 
        throws InvalidCredentials, AuthenticationTimeout;
    public void isAuthorized(AuthToken token, String method, IdentifierType guid)
        throws InvalidToken, NotFound;
    public boolean verify(AuthToken token);
}
