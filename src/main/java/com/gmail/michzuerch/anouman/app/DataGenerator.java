package com.gmail.michzuerch.anouman.app;

import com.gmail.michzuerch.anouman.backend.data.Role;
import com.gmail.michzuerch.anouman.backend.data.entity.*;
import com.gmail.michzuerch.anouman.backend.repositories.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@SpringComponent
public class DataGenerator implements HasLogger {
    private final Random random = new Random(1L);

    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private ArticleCategoryRepository articleCategoryRepository;
    private ArticlePictureRepository articlePictureRepository;
    private ArticleRepository articleRepository;
    private BookEntryRepository bookEntryRepository;
    private BookkeepingRepository bookkeepingRepository;
    private MehrwertsteuercodeRepository mehrwertsteuercodeRepository;
    private EffortRepository effortRepository;
    private InvoiceRepository invoiceRepository;
    private InvoiceDetailRepository invoiceDetailRepository;
    private KontogruppeRepository kontogruppeRepository;
    private KontoHauptgruppeRepository kontoHauptgruppeRepository;
    private KontoklasseRepository kontoklasseRepository;
    private KontoRepository kontoRepository;
    private UnterbuchungRepository unterbuchungRepository;
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
                         MehrwertsteuercodeRepository mehrwertsteuercodeRepository,
                         EffortRepository effortRepository,
                         InvoiceRepository invoiceRepository,
                         InvoiceDetailRepository invoiceDetailRepository,
                         KontogruppeRepository kontogruppeRepository,
                         KontoHauptgruppeRepository kontoHauptgruppeRepository,
                         KontoklasseRepository kontoklasseRepository,
                         KontoRepository kontoRepository,
                         UnterbuchungRepository unterbuchungRepository,
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
        this.mehrwertsteuercodeRepository = mehrwertsteuercodeRepository;
        this.effortRepository = effortRepository;
        this.invoiceRepository = invoiceRepository;
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.kontogruppeRepository = kontogruppeRepository;
        this.kontoHauptgruppeRepository = kontoHauptgruppeRepository;
        this.kontoklasseRepository = kontoklasseRepository;
        this.kontoRepository = kontoRepository;
        this.unterbuchungRepository = unterbuchungRepository;
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

        getLogger().info("... generating articles");
        createArticles(articleRepository, articleCategoryRepository, articlePictureRepository);

        getLogger().info("... generating template bookkeeping");
        createTemplateBookkepping(templateBookkeepingRepository, templateKontoRepository, templateKontogruppeRepository,
                templateKontoHauptgruppeRepository, templateKontoklasseRepository, templateMehrwertsteuercodeRepository);

        getLogger().info("... generating bookkepping");
        createBookkeeping(bookkeepingRepository, kontoklasseRepository, kontoHauptgruppeRepository,
                kontogruppeRepository, kontoRepository);

        stopWatch.stop();
        getLogger().info("Generated demo data. Time:" + stopWatch.getTotalTimeMillis() + "ms.");

    }

    private void createBookkeeping(BookkeepingRepository bookkeepingRepository, KontoklasseRepository kontoklasseRepository, KontoHauptgruppeRepository kontoHauptgruppeRepository, KontogruppeRepository kontogruppeRepository, KontoRepository kontoRepository) {
        Bookkeeping bookkeeping = new Bookkeeping.Builder()
                .description("Bookkeeping Testdata")
                .year(2018)
                .build();

        bookkeeping = bookkeepingRepository.save(bookkeeping);

        Mehrwertsteuercode mehrwertsteuercode = new Mehrwertsteuercode.Builder()
                .description("Verkauf 12%")
                .percentage(BigDecimal.valueOf(12))
                .selling(true)
                .bookkeeping(bookkeeping)
                .build();

        mehrwertsteuercode = mehrwertsteuercodeRepository.save(mehrwertsteuercode);

        Kontoklasse kontoklasse = new Kontoklasse.Builder()
                .description("Kontoklasse")
                .kontonummer("1000")
                .bookkeeping(bookkeeping)
                .build();

        kontoklasse = kontoklasseRepository.save(kontoklasse);

        KontoHauptgruppe kontoHauptgruppe = new KontoHauptgruppe.Builder()
                .description("Konto Hauptgruppe")
                .kontonummer("2000")
                .kontoklasse(kontoklasse)
                .build();

        kontoHauptgruppe = kontoHauptgruppeRepository.save(kontoHauptgruppe);

        Kontogruppe kontogruppe = new Kontogruppe.Builder()
                .description("Kontogruppe")
                .kontonummer("3000")
                .kontoHauptgruppe(kontoHauptgruppe)
                .build();

        kontogruppe = kontogruppeRepository.save(kontogruppe);

        Konto konto = new Konto.Builder()
                .anfangsbestand(BigDecimal.ZERO)
                .comment("Comment")
                .description("Konto Testdata")
                .kontonummer("5000")
                .kontogruppe(kontogruppe)
                .build();

        konto = kontoRepository.save(konto);

        BookEntry bookEntry = new BookEntry.Builder()
                .belegnummer("1")
                .bookkeeping(bookkeeping)
                .buchungsdatum(LocalDate.now())
                .build();

        bookEntry = bookEntryRepository.save(bookEntry);

        Unterbuchung unterbuchung = new Unterbuchung.Builder()
                .buchungstext("Testbuchung aus Test")
                .betrag(BigDecimal.valueOf(230))
                .kontoHaben(konto)
                .build();

        unterbuchung = unterbuchungRepository.save(unterbuchung);
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
                .invoice(invoice)
                .build();
        invoiceDetail1 = invoiceDetailRepository.save(invoiceDetail1);

        InvoiceDetail invoiceDetail2 = new InvoiceDetail.Builder().invoice(invoice)
                .decription("Drucker")
                .descriptionLong("HP Laser 8520L")
                .quantityUnit("Stück")
                .quantity(BigDecimal.valueOf(3))
                .unitPrice(BigDecimal.valueOf(1156.54))
                .invoice(invoice)
                .build();
        invoiceDetail2 = invoiceDetailRepository.save(invoiceDetail2);

        Effort effort = new Effort.Builder()
                .title("Aufwand")
                .description("Aufwand Test")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .movable(false)
                .resizeable(true)
                .invoice(invoice)
                .build();
        effort = effortRepository.save(effort);


    }

    private void createArticles(ArticleRepository articleRepository, ArticleCategoryRepository articleCategoryRepository, ArticlePictureRepository articlePictureRepository) {
        ArticleCategory articleCategory = new ArticleCategory.Builder().description("First Category").build();
        articleCategory = articleCategoryRepository.save(articleCategory);

        Article article = new Article.Builder().articleCategory(articleCategory)
                .description("Short description")
                .descriptionLong("A very long description")
                .quantityUnit("Stück")
                .stueckpreis(BigDecimal.valueOf(120))
                .articleCategory(articleCategory)
                .build();
        article = articleRepository.save(article);

        ArticlePicture articlePicture = new ArticlePicture.Builder().titel("Title for sample")
                .mimetype("jpg")
                .article(article)
                .build();
        articlePicture = articlePictureRepository.save(articlePicture);

    }

    private void createTemplateBookkepping(TemplateBookkeepingRepository templateBookkeepingRepository,
                                           TemplateKontoRepository templateKontoRepository, TemplateKontogruppeRepository templateKontogruppeRepository,
                                           TemplateKontoHauptgruppeRepository templateKontoHauptgruppeRepository,
                                           TemplateKontoklasseRepository templateKontoklasseRepository, TemplateMehrwertsteuercodeRepository templateMehrwertsteuercodeRepository) {
        TemplateBookkeeping templateBookkeeping = new TemplateBookkeeping.Builder()
                .description("Template Bookkeeping sample")
                .year(2017)
                .build();

        templateBookkeeping = templateBookkeepingRepository.save(templateBookkeeping);

        TemplateMehrwertsteuercode templateMehrwertsteuercode1 = new TemplateMehrwertsteuercode.Builder()
                .description("Einkauf 12%")
                .percentage(BigDecimal.valueOf(12))
                .selling(false)
                .templateBookkeeping(templateBookkeeping)
                .build();

        templateMehrwertsteuercode1 = templateMehrwertsteuercodeRepository.save(templateMehrwertsteuercode1);

        TemplateMehrwertsteuercode templateMehrwertsteuercode2 = new TemplateMehrwertsteuercode.Builder()
                .description("Verkauf 12%")
                .percentage(BigDecimal.valueOf(12))
                .selling(true)
                .templateBookkeeping(templateBookkeeping)
                .build();

        templateMehrwertsteuercode2 = templateMehrwertsteuercodeRepository.save(templateMehrwertsteuercode2);

        TemplateKontoklasse templateKontoklasse = new TemplateKontoklasse.Builder()
                .description("Kontoklasse")
                .kontonummer("1000")
                .templateBookkeeping(templateBookkeeping)
                .build();

        templateKontoklasse = templateKontoklasseRepository.save(templateKontoklasse);

        TemplateKontoHauptgruppe templateKontoHauptgruppe = new TemplateKontoHauptgruppe.Builder()
                .description("Kontohauptgruppe")
                .kontonummer("2000")
                .templateKontoklasse(templateKontoklasse)
                .build();

        templateKontoHauptgruppe = templateKontoHauptgruppeRepository.save(templateKontoHauptgruppe);

        TemplateKontogruppe templateKontogruppe = new TemplateKontogruppe.Builder()
                .description("Kontogruppe")
                .kontonummer("3000")
                .templateKontoHauptgruppe(templateKontoHauptgruppe)
                .build();

        templateKontogruppe = templateKontogruppeRepository.save(templateKontogruppe);

        TemplateKonto templateKonto = new TemplateKonto.Builder()
                .description("TemplateKonto")
                .kontonummer("5400")
                .templateKontogruppe(templateKontogruppe)
                .build();

        templateKonto = templateKontoRepository.save(templateKonto);

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
