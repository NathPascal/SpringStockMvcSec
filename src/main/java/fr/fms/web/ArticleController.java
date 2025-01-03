package fr.fms.web;

import fr.fms.dao.CustomerRepository;
import fr.fms.entities.Customer;
import fr.fms.service.Cart;
import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, CategoryRepository categoryRepository, CustomerRepository customerRepository ){
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/index")
    public String index(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                        @RequestParam(name = "keyword", defaultValue = "") String kw,
                        @RequestParam(name = "categoryId", defaultValue = "0") Long categoryId) {
        Page<Article> articles;
        if (categoryId > 0) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            articles = articleRepository.findByCategoryAndDescriptionContains(category, kw, PageRequest.of(page, 5));
        } else {
            articles = articleRepository.findByDescriptionContains(kw, PageRequest.of(page, 5));
        }
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("listArticle", articles.getContent());
        model.addAttribute("pages", new int[articles.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);
        model.addAttribute("selectedCategoryId", categoryId);
        return "articles";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id, @RequestParam int page,@RequestParam String keyword){
        articleRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/article")
    public String article(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("categories", categoryRepository.findAll());
        return "article";
    }

    @PostMapping("/save")
    public String save(@Valid Article article, BindingResult bindingResult){
        if(bindingResult.hasErrors())  return "article";
        if (article.getCategory() !=null) {
            Category category = categoryRepository.findById(article.getCategory().getId()).orElse(null);
            if (category != null) {
                article.setCategory(category);
            }
        }
        System.out.println(article);
        articleRepository.save(article);
        return "redirect:/index";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id){
        Article article = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", article);
        model.addAttribute("categories", categoryRepository.findAll());
        return "article";
    }

    @GetMapping("/403")
    public String error(){
        return "403";
    }

    @GetMapping("/cart")
    public String cart(HttpSession httpSession, Model model){
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart == null){
            cart = new Cart();
            httpSession.setAttribute("cart", cart);
        }
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/addToCart")
    public String addToCart (@RequestParam Long id, HttpSession httpSession, Model model){
        Article article = articleRepository.findById(id).orElse(null);
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart == null){
                cart = new Cart();
                httpSession.setAttribute("cart", cart);
        }
        else cart.addItem(article);

        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/validateCart")
    public String validateCart(HttpSession httpSession, Model model) {
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            model.addAttribute("error", "Votre panier est vide.");
            return "cart";
        }
        return "redirect:/customer";
    }

    @GetMapping("/customer")
    public String customerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer";
    }

    @PostMapping("/createCustomer")
    public String createCustomer(@Valid Customer customer, HttpSession httpSession, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "customer";
        }
        customerRepository.save(customer);
        httpSession.setAttribute("customer", customer);
        return "redirect:/order";
    }

    @GetMapping("/order")
    public String order(HttpSession httpSession, Model model){
        Cart cart = (Cart) httpSession.getAttribute("cart");
        Customer customer = (Customer) httpSession.getAttribute("customer");
        if (cart == null || cart.getItems().isEmpty() || customer == null) {
            return "redirect:/cart";
        }
        model.addAttribute("cart", cart);
        model.addAttribute("customer", customer);
        return "order";
    }

    @PostMapping("/confirmOrder")
    public String confirmOrder(HttpSession httpSession){
        httpSession.removeAttribute("cart");
        httpSession.removeAttribute("customer");
        return "redirect:/index";
    }
}
