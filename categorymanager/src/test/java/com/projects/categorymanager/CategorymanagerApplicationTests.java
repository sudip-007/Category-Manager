package com.projects.categorymanager;

import com.projects.categorymanager.entities.CategoryEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class CategorymanagerApplicationTests {

	private StandardServiceRegistry registry = null;
	private SessionFactory factory= null;
	private Session session = null;

	void init(){
		registry = new StandardServiceRegistryBuilder()
				.configure()
				.build();

		factory = new MetadataSources(registry)
				.buildMetadata()
				.buildSessionFactory();

		session = factory.openSession();
	}

	void addCategories() {
		CategoryEntity electronics = new CategoryEntity("Electronics");
		CategoryEntity mobilePhones = new CategoryEntity("Mobile phones", electronics);
		CategoryEntity washingMachines = new CategoryEntity("Washing machines", electronics);

		electronics.addChild(mobilePhones);
		electronics.addChild(washingMachines);

		CategoryEntity iPhone = new CategoryEntity("iPhone", mobilePhones);
		CategoryEntity samsung = new CategoryEntity("Samsung", mobilePhones);

		mobilePhones.addChild(iPhone);
		mobilePhones.addChild(samsung);

		CategoryEntity galaxy = new CategoryEntity("Galaxy", samsung);
		samsung.addChild(galaxy);

		session.save(electronics);
	}

	private static void printCategories(Session session) {
		CategoryEntity electronics = session.get(CategoryEntity.class, 1);

		Set<CategoryEntity> children = electronics.getChildren();

		System.out.println(electronics.getName());

		for (CategoryEntity child : children) {
			System.out.println("--" + child.getName());
			printChildren(child, 1);
		}
	}

	private static void printChildren(CategoryEntity parent, int subLevel) {
		Set<CategoryEntity> children = parent.getChildren();

		for (CategoryEntity child : children) {
			for (int i = 0; i <= subLevel; i++) System.out.print("--");

			System.out.println(child.getName());

			printChildren(child, subLevel + 1);
		}
	}

	@Test
	void contextLoads() {
		init();
//		addCategories();
		printCategories(this.session);
		destroy();
	}

	void destroy() {
		session.close();
		factory.close();
	}

}
