package telran.security.accounting.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.security.accounting.dto.AccountDto;
import telran.security.accounting.model.Account;
import telran.security.accounting.repo.AccountRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountingServiceImpl implements AccountingService {
final AccountRepo accountRepo;
final PasswordEncoder passwordEncoder;
	@Override
	public AccountDto addAccount(AccountDto accountDto) {
		String email = accountDto.email();
		AccountDto accountEncoded = getAccountDtoEncoded(accountDto);
		if(accountRepo.existsById(email)) {
			throw new IllegalStateException(
					String.format("account %s already exists", email));			
		}
		Account account = accountRepo.save(Account.of(accountEncoded));
		log.debug("acount {} has been saved", email);
		return account.build();
	}

	private AccountDto getAccountDtoEncoded(AccountDto accountDto) {
		return new AccountDto(accountDto.email(), 
				passwordEncoder.encode(accountDto.password()), accountDto.roles());
	}

	@Override
	public AccountDto removeAccount(String email) {
		Account account = accountRepo.findById(email)
				.orElseThrow(() -> new IllegalStateException(
						String.format("account %s doesn't exist", email)));
		accountRepo.delete(account);
		log.debug("account {} has been removed", email);
		return account.build();
	}

}
