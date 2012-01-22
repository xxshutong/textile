/*
 * Copyright (c) Open Source Strategies, Inc.
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
package org.opentaps.testsuit.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.validator.GenericValidator;
import org.opentaps.tests.BaseTestCase;


/**
 *
 */
@SuppressWarnings("serial")
public class IntegrTestsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String serviceName = request.getParameter("service");
            String testName = request.getParameter("test");
            if (GenericValidator.isBlankOrNull(serviceName) || GenericValidator.isBlankOrNull(testName)) {
                throw new ServletException("Missing required parameters.");
            }

            InitialContext context = new InitialContext();
            Object testSrvc = context.lookup(String.format("osgi:service/%1$s", serviceName));
            if (testSrvc == null) {
                throw new ServletException("Test service is unavailable.");
            }

            PrintWriter out = response.getWriter();

            try {
                Method testCase = ClassUtils.getPublicMethod(testSrvc.getClass(), testName, new Class[0]);
                testCase.invoke(testSrvc, new Object[]{});

                out.println(BaseTestCase.SUCCESS_RET_CODE);

            } catch (InvocationTargetException e) {
                out.println(e.getTargetException().getLocalizedMessage());
                return;
            }

        } catch (NamingException e) {
            throw new ServletException(e);
        } catch (IllegalArgumentException e) {
            throw new ServletException(e);
        } catch (IllegalAccessException e) {
            throw new ServletException(e);
        } catch (SecurityException e) {
            throw new ServletException(e);
        } catch (NoSuchMethodException e) {
            throw new ServletException(e);
        }
    }

}
