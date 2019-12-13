package com.gmail.michzuerch.anouman.app;

import com.gmail.michzuerch.anouman.backend.data.Role;
import com.gmail.michzuerch.anouman.backend.data.entity.Address;
import com.gmail.michzuerch.anouman.backend.data.entity.Invoice;
import com.gmail.michzuerch.anouman.backend.data.entity.InvoiceDetail;
import com.gmail.michzuerch.anouman.backend.data.entity.User;
import com.gmail.michzuerch.anouman.backend.repositories.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

@SpringComponent
public class DataGenerator implements HasLogger {

    private static final String[] FILLING = new String[]{"Strawberry", "Chocolate", "Blueberry", "Raspberry",
            "Vanilla"};
    private static final String[] TYPE = new String[]{"Cake", "Pastry", "Tart", "Muffin", "Biscuit", "Bread", "Bagel",
            "Bun", "Brownie", "Cookie", "Cracker", "Cheese Cake"};
    private static final String[] FIRST_NAME = new String[]{"Ori", "Amanda", "Octavia", "Laurel", "Lael", "Delilah",
            "Jason", "Skyler", "Arsenio", "Haley", "Lionel", "Sylvia", "Jessica", "Lester", "Ferdinand", "Elaine",
            "Griffin", "Kerry", "Dominique"};
    private static final String[] LAST_NAME = new String[]{"Carter", "Castro", "Rich", "Irwin", "Moore", "Hendricks",
            "Huber", "Patton", "Wilkinson", "Thornton", "Nunez", "Macias", "Gallegos", "Blevins", "Mejia", "Pickett",
            "Whitney", "Farmer", "Henry", "Chen", "Macias", "Rowland", "Pierce", "Cortez", "Noble", "Howard", "Nixon",
            "Mcbride", "Leblanc", "Russell", "Carver", "Benton", "Maldonado", "Lyons"};

    private final Random random = new Random(1L);

    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private ArticleCategoryRepository articleCategoryRepository;
    private ArticlePictureRepository articlePictureRepository;
    private ArticleRepository articleRepository;
    private BookEntryRepository bookEntryRepository;
    private BookkeepingRepository bookkeepingRepository;
    private EffortRepository effortRepository;
    private InvoiceRepository invoiceRepository;
    private InvoiceDetailRepository invoiceDetailRepository;
    private KontogruppeRepository kontogruppeRepository;
    private KontoHauptgruppeRepository kontoHauptgruppeRepository;
    private KontoklasseRepository kontoklasseRepository;
    private KontoRepository kontoRepository;
    private TemplateBookkeepingRepository templateBookkeepingRepository;
    private TemplateKontogruppeRepository templateKontogruppeRepository;
    private TemplateKontoHauptgruppeRepository templateKontoHauptgruppeRepository;
    private TemplateKontoklasseRepository templateKontoklasseRepository;
    private TemplateKontoRepository templateKontoRepository;
    private TemplateMehrwertsteuercodeRepository templateMehrwertsteuercodeRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataGenerator(UserRepository userRepository,
                         AddressRepository addressRepository,
                         ArticleCategoryRepository articleCategoryRepository,
                         ArticlePictureRepository articlePictureRepository,
                         ArticleRepository articleRepository,
                         BookEntryRepository bookEntryRepository,
                         BookkeepingRepository bookkeepingRepository,
                         EffortRepository effortRepository,
                         InvoiceRepository invoiceRepository,
                         InvoiceDetailRepository invoiceDetailRepository,
                         KontogruppeRepository kontogruppeRepository,
                         KontoHauptgruppeRepository kontoHauptgruppeRepository,
                         KontoklasseRepository kontoklasseRepository,
                         KontoRepository kontoRepository,
                         TemplateBookkeepingRepository templateBookkeepingRepository,
                         TemplateKontogruppeRepository templateKontogruppeRepository,
                         TemplateKontoHauptgruppeRepository templateKontoHauptgruppeRepository,
                         TemplateKontoklasseRepository templateKontoklasseRepository,
                         TemplateKontoRepository templateKontoRepository,
                         TemplateMehrwertsteuercodeRepository templateMehrwertsteuercodeRepository,
                         PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.articleCategoryRepository = articleCategoryRepository;
        this.articlePictureRepository = articlePictureRepository;
        this.articleRepository = articleRepository;
        this.bookEntryRepository = bookEntryRepository;
        this.bookkeepingRepository = bookkeepingRepository;
        this.effortRepository = effortRepository;
        this.invoiceRepository = invoiceRepository;
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.kontogruppeRepository = kontogruppeRepository;
        this.kontoHauptgruppeRepository = kontoHauptgruppeRepository;
        this.kontoklasseRepository = kontoklasseRepository;
        this.kontoRepository = kontoRepository;
        this.templateBookkeepingRepository = templateBookkeepingRepository;
        this.templateKontogruppeRepository = templateKontogruppeRepository;
        this.templateKontoHauptgruppeRepository = templateKontoHauptgruppeRepository;
        this.templateKontoklasseRepository = templateKontoklasseRepository;
        this.templateKontoRepository = templateKontoRepository;
        this.templateMehrwertsteuercodeRepository = templateMehrwertsteuercodeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void loadData() {
        StopWatch stopWatch = new StopWatch("Anouman DataGenerator.loadData()");
        stopWatch.start();
        if (userRepository.count() != 0L) {
            getLogger().info("Using existing database");
            return;
        }

        getLogger().info("Generating demo data");

        getLogger().info("... generating users");
        User baker = createBaker(userRepository, passwordEncoder);
        User barista = createBarista(userRepository, passwordEncoder);
        createAdmin(userRepository, passwordEncoder);
        // A set of products without constrains that can be deleted
        createDeletableUsers(userRepository, passwordEncoder);

        getLogger().info("... generating addresses");
        createAddressesAndInvoices(addressRepository, invoiceRepository);

        stopWatch.stop();
        getLogger().info("Generated demo data. Time:" + stopWatch.getTotalTimeMillis() + "ms.");

    }

    private void createAddressesAndInvoices(AddressRepository addressRepository, InvoiceRepository invoiceRepository) {
        Address address = new Address.Builder()
                .companyName("Internettechnik GmbH")
                .salutation("Herr")
                .firstname("Michael").lastname("Zürcher").street("Industriestrasse 5")
                .zipcode("83234").city("Pfarrenkapp").hourlyRate(BigDecimal.valueOf(150))
                .build();

        address = addressRepository.save(address);

        Invoice invoice = new Invoice.Builder().address(address)
                .date(LocalDate.now())
                .description("Testinvoice")
                .forwarded(true)
                .paid(false)
                .timeForPayment(60)
                .build();
        invoice = invoiceRepository.save(invoice);

        InvoiceDetail invoiceDetail1 = new InvoiceDetail.Builder().invoice(invoice)
                .decription("Notebook")
                .descriptionLong("Acer Aspire 5 8756-E")
                .quantityUnit("Stück")
                .quantity(BigDecimal.valueOf(1))
                .unitPrice(BigDecimal.valueOf(2351.15))
                .build();
        invoiceDetail1 = invoiceDetailRepository.save(invoiceDetail1);

        InvoiceDetail invoiceDetail2 = new InvoiceDetail.Builder().invoice(invoice)
                .decription("Drucker")
                .descriptionLong("HP Laser 8520L")
                .quantityUnit("Stück")
                .quantity(BigDecimal.valueOf(3))
                .unitPrice(BigDecimal.valueOf(1156.54))
                .build();
        invoiceDetail2 = invoiceDetailRepository.save(invoiceDetail2);

    }


    private LocalTime getRandomDueTime() {
        int time = 8 + 4 * random.nextInt(3);

        return LocalTime.of(time, 0);
    }


    private <T> T getRandom(T[] array) {
        return array[random.nextInt(array.length)];
    }

    private String getRandomProductName() {
        String firstFilling = getRandom(FILLING);
        String name;
        if (random.nextBoolean()) {
            String secondFilling;
            do {
                secondFilling = getRandom(FILLING);
            } while (secondFilling.equals(firstFilling));

            name = firstFilling + " " + secondFilling;
        } else {
            name = firstFilling;
        }
        name += " " + getRandom(TYPE);

        return name;
    }

    private User createBaker(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return userRepository.save(
                createUser("baker@vaadin.com", "Heidi", "Carter", passwordEncoder.encode("baker"), Role.BAKER, false));
    }

    private User createBarista(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return userRepository.save(createUser("barista@michzuerch.gmail.com", "Malin", "Castro",
                passwordEncoder.encode("pass"), Role.BARISTA, true));
    }

    private User createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return userRepository.save(createUser("admin@michzuerch.gmail.com", "Michael", "Zürcher",
                passwordEncoder.encode("admin"), Role.ADMIN, true));
    }

    private void createDeletableUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        userRepository.save(
                createUser("peter@vaadin.com", "Peter", "Bush", passwordEncoder.encode("peter"), Role.BARISTA, false));
        userRepository
                .save(createUser("mary@vaadin.com", "Mary", "Ocon", passwordEncoder.encode("mary"), Role.BAKER, true));
    }

    private User createUser(String email, String firstName, String lastName, String passwordHash, String role,
                            boolean locked) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPasswordHash(passwordHash);
        user.setRole(role);
        user.setLocked(locked);
        return user;
    }
}
