/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/send")
public class SenderServlet extends HttpServlet {

    public static final String SENDER_MESSAGES_KEY = SenderServlet.class + "_SENDER_MESSAGES_KEY";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // メッセージ送信
        List<String> messages = new ArrayList<String>();
        MessageSender sender = new MessageSender();
        for ( int i=0; i < 10; i++ ) {
            String message = "testMessage["+i+"]";
            sender.send(message);
            messages.add(message);
        }
        
        // 画面へ引き継ぐ
        req.setAttribute(SENDER_MESSAGES_KEY, messages);
        
        // jspへフォワード
        String disp = "/output.jsp";
        RequestDispatcher dispatch = getServletContext().getRequestDispatcher(disp);
        dispatch.forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
