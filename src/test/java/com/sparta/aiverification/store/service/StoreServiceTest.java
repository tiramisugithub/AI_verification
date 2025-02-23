package com.sparta.aiverification.store.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import com.sparta.aiverification.category.repository.CategoryRepository;
import com.sparta.aiverification.menu.repository.MenuRepository;
import com.sparta.aiverification.region.repository.RegionRepository;
import com.sparta.aiverification.review.entity.Review;
import com.sparta.aiverification.store.entity.Store;
import com.sparta.aiverification.store.repository.StoreRepository;

@SpringBootTest
class StoreServiceTest {

	@Autowired
	StoreService storeService;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	MenuRepository menuRepository;

	@Autowired
	RegionRepository regionRepository;

	@Autowired
	CategoryRepository categoryRepository;
	@Test
	void getAllStoresByJoin() {
		List<Store> testStoreListUsingJoin = storeService.findAllWithReviewUsingJoin();
		System.out.println(testStoreListUsingJoin.get(0).getReviews());
	}

	@Test
	void getAllStoresByFetchJoin() {
		List<Store> testStoreListUsingJoin = storeService.findAllWithReviewUsingFetchJoin();
		for(Store store : testStoreListUsingJoin){
			System.out.println(store.getName());
			int tmp = 0;
			for(Review review : store.getReviews()){
				tmp += review.getScore();
				System.out.println(tmp);
			}
		}
		System.out.println(testStoreListUsingJoin.size());
	}

	@Test
	void getAllStoresByEntityGraph() {
		List<Store> testStoreListUsingJoin = storeService.findAllWithReviewUsingEntityGraph();
		for(Store store : testStoreListUsingJoin){
			System.out.println(store.getName());
			int tmp = 0;
			for(Review review : store.getReviews()){
				tmp += review.getScore();
				System.out.println(tmp);
			}
		}
		System.out.println(testStoreListUsingJoin.size());
	}

	@Test
	void getAllStoresByFetchMode(){
		List<Store> testStoreListUsingFetchMode = storeService.findAll();
		for(Store store : testStoreListUsingFetchMode){
			System.out.println(store.getName());
			int tmp = 0;
			for(Review review : store.getReviews()){
				tmp += review.getScore();
				System.out.println(tmp);
			}
		}
		System.out.println(testStoreListUsingFetchMode.size());
	}

	@Test
	void getAllStoresReturnPageWithFetchJoin() {
		Pageable pageable = storeService.getPageable(false, 1, 10, "createdAt");
		Page<Store> testStoreList = storeService.findAllReturnPageWithFetchJoin(pageable);
		System.out.println(testStoreList);
	}

	@Test
	void getAllStoresReturnPageWith(){
		Pageable pageable = storeService.getPageable(false, 1, 10, "createdAt");
		List<Store> testStoreList = storeService.findAllReturnPageWithEntityGraph(pageable).getContent();
		for(Store store : testStoreList){
			System.out.println(store.getName());
			int tmp = 0;
			for(Review review : store.getReviews()){
				tmp += review.getScore();
				System.out.println(tmp);
			}
		}
		System.out.println(testStoreList.size());
	}
}