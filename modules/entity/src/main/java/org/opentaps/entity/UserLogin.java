package org.opentaps.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.opentaps.entity.model.IUserLogin;


@Entity(name = "UserLogin")
@Table(name = "USER_LOGIN")
public class UserLogin implements IUserLogin {

    @Id
    @Column(name = "USER_LOGIN_ID", nullable = false)
    private String userLoginId;

    @Column(name = "CURRENT_PASSWORD")
    private String currentPassword;

    @Column(name = "PASSWORD_HINT")
    private String passwordHint;

    @Column(name = "IS_SYSTEM")
    private String isSystem;

    @Column(name = "ENABLED")
    private String enabled;

    @Column(name = "HAS_LOGGED_OUT")
    private String hasLoggedOut;

    @Column(name = "REQUIRE_PASSWORD_CHANGE")
    private String requirePasswordChange;

    @Column(name = "LAST_CURRENCY_UOM")
    private String lastCurrencyUom;

    @Column(name = "LAST_LOCALE")
    private String lastLocale;

    @Column(name = "LAST_TIME_ZONE")
    private String lastTimeZone;

    @Column(name = "DISABLED_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date disabledDateTime;

    @Column(name = "SUCCESSIVE_FAILED_LOGINS")
    private Long successiveFailedLogins;

    @Column(name = "EXTERNAL_AUTH_ID")
    private String externalAuthId;

    @Column(name = "USER_LDAP_DN")
    private String userLdapDn;

    @Column(name = "PARTY_ID")
    private String partyId;

	public UserLogin() {
	    // do nothing
	}

}
