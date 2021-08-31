package za.ac.up.cs.dynative.EParkSmartWaterMonitor.security.former;

import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.*;
import java.io.IOException;

class NoRedirectStrategy implements RedirectStrategy {

    @Override
    public void sendRedirect(final HttpServletRequest request, final HttpServletResponse response, final String url) throws IOException {
    }
}