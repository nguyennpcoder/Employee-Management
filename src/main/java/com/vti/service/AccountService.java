package com.vti.service;

import com.vti.entity.Account;
import com.vti.form.AccountCreateForm;
import com.vti.form.AccountFilterForm;
import com.vti.form.AccountUpdateForm;
import com.vti.repository.IAccountRepository;
import com.vti.specification.AccountSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Service
public class AccountService implements IAccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class);

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private IAccountRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Page<Account> findAll(Pageable pageable, AccountFilterForm form) {
        Specification<Account> spec = AccountSpecification.buildSpec(form);
        return repository.findAll(spec, pageable);
    }

    @Override
    public Account findById(int id) {
        logger.info("Start findByid: " + id);
        Account account = repository.findById(id).orElse(null);
        assert account != null;
        account.setPassword(null);
        return account;
    }

    @Override
    public void create(AccountCreateForm form) {
        Account account = mapper.map(form, Account.class);
        // 500x return Pass
        // 400x k return Pass
        String encodedPassword = encoder.encode(form.getPassword());
        account.setPassword(encodedPassword);
        repository.save(account);
    }

    @Override
    public void update(AccountUpdateForm form) {
        try {
            Properties properties = new Properties();
            int soLaBiReport = Integer.parseInt(properties.getProperty("soLanBiReport"));
            Account account = mapper.map(form, Account.class);
            String encodedPassword = encoder.encode(form.getPassword());
            account.setPassword(encodedPassword);
            //log sve todb
            repository.save(account);
        } catch (Exception e) {
            logger.error("Can not update Account: "+ form.toString());
        }
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllById(List<Integer> ids) {
        repository.deleteAllById(ids);
    }

    // ke thua UserDetails: tim kiem ng dung
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("Tài khoản không tồn tại");
        }
        return new User(
                account.getUsername(),
                account.getPassword(),
                AuthorityUtils.createAuthorityList(account.getRole().toString())
        );

    }
}
