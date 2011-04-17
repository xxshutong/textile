/*
 * Copyright (c) 2011 Open Source Strategies, Inc.
 * 
 * Opentaps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Opentaps is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opentaps.testsuit.web.jpa;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentaps.testsuit.jpa.IJPATestService;

public class JPATestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            InitialContext context = new InitialContext();
            IJPATestService testSrvc = (IJPATestService) context.lookup("osgi:service/JPATestService");
            if (testSrvc == null) {
                //TODO: report service unavailable
            }

            testSrvc.insertTestEntityWithJTA();

            PrintWriter out = response.getWriter();
            out.println("<html><head><title>OSGi JPA Test Application</title></head></html>");
            out.println("<body><h1>");
            out.println("Hello World!");
            out.println("</h1></body></html>");

        } catch (NamingException e) {
            throw new ServletException(e);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        
    }

}
