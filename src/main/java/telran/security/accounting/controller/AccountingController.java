package telran.security.accounting.controller;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.security.accounting.dto.AccountDto;
import telran.security.accounting.service.AccountingService;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountingController {
	final AccountingService accountingService;	
	@PostMapping
	public AccountDto addAccount(@RequestBody @Valid AccountDto accountDto) {
		return accountingService.addAccount(accountDto);
	}	
	@DeleteMapping("{email}")
	public AccountDto removeAccount(@PathVariable @Valid String email) {
		return accountingService.removeAccount(email);
	}
}
