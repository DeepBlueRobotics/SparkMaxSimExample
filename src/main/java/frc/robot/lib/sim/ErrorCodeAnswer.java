package frc.robot.lib.sim;

import com.ctre.phoenix.ErrorCode;

import org.mockito.internal.stubbing.defaultanswers.ReturnsSmartNulls;
import org.mockito.invocation.InvocationOnMock;

/**
 * Represents a {@link org.mockito.Mockito} {@link org.mockito.stubbing.Answer} which returns {@link ErrorCode#OK}
 * for methods which return an {@link ErrorCode} object and refers all other calls to {@link ReturnsSmartNulls}
 */
public class ErrorCodeAnswer extends ReturnsSmartNulls {

    private static final long serialVersionUID = -561160298532167923L;

    @Override
    public Object answer(InvocationOnMock invocation) throws Throwable {
        return invocation.getMethod().getReturnType().equals(ErrorCode.class) ? ErrorCode.OK : super.answer(invocation);
    }
    
}