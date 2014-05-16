/*******************************************************************************
 * Copyright (c) 2014 hangum.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     hangum - initial API and implementation
 ******************************************************************************/
package com.hangum.tadpold.commons.libs.core.mails;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import com.hangum.tadpold.commons.libs.core.mails.dto.EmailDTO;
import com.hangum.tadpold.commons.libs.core.mails.dto.SMTPDTO;

/**
 * SendEmail
 * 
 * @author hangum
 *
 */
public class SendEmails {
	private static final Logger logger = Logger.getLogger(SendEmails.class);
	SMTPDTO smtpDto;
	
	public SendEmails(SMTPDTO smtpDto) {
		this.smtpDto = smtpDto;
	}

	/**
	 * send email
	 * 
	 * @param emailDao
	 */
	public void sendMail(EmailDTO emailDao) {
		if(logger.isDebugEnabled()) logger.debug("Add new message");
		
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName(smtpDto.getHost());
			email.setSmtpPort(NumberUtils.toInt(smtpDto.getPort()));
			email.setAuthenticator(new DefaultAuthenticator(smtpDto.getEmail(), smtpDto.getPasswd()));
			email.setSSLOnConnect(true);
	
			email.setFrom(smtpDto.getEmail());
			email.setSubject(emailDao.getSubject());
			
			// set the html message
			email.setHtmlMsg(emailDao.getContent());
			
			email.addTo(emailDao.getTo());
			email.send();
			
		} catch(Exception e) {
			logger.error("send email", e);
		}
	}
}