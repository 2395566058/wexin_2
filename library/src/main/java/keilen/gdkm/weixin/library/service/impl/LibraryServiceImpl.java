package keilen.gdkm.weixin.library.service.impl;

import keilen.gdkm.weixin.library.domain.Book;
import keilen.gdkm.weixin.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Page<Book> search(String keyword, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 3);
		Page<Book> page;
		if (StringUtils.isEmpty(keyword)) {
			page = this.bookRepository.findByDisabledFalse(pageable);
		} else {
			page = this.bookRepository.findByNameContainingAndDisabledFalse(keyword, pageable);
		}

		return page;
	}
}
