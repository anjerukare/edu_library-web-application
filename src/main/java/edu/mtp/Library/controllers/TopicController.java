package edu.mtp.Library.controllers;

import edu.mtp.Library.dao.TopicDao;
import edu.mtp.Library.dao.UserDao;
import edu.mtp.Library.models.Topic;
import edu.mtp.Library.models.TopicReply;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Random;

import static java.lang.Math.abs;

@Controller
@RequestMapping("/topics")
public class TopicController {

    private final TopicDao topicDao;
    private final UserDao userDao;

    private final static Random random = new Random();

    public TopicController(TopicDao topicDao, UserDao userDao) {
        this.topicDao = topicDao;
        this.userDao = userDao;
    }

    @GetMapping("/{id}")
    public String getTopic(@PathVariable int id, @ModelAttribute TopicReply topicReply, Model model) {
        model.addAttribute("topic", topicDao.get(id));
        model.addAttribute("random", abs(random.nextInt()));
        return "topics/get";
    }

    @GetMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public String newTopic(@ModelAttribute Topic topic, @ModelAttribute TopicReply topicReply) {
        return "topics/new";
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String addTopic(@ModelAttribute @Valid Topic topic, BindingResult topicBindingResult,
                           @ModelAttribute @Valid TopicReply reply, BindingResult replyBindingResult,
                           Principal principal) {
        if (topicBindingResult.hasErrors() || replyBindingResult.hasErrors()) {
            return "topics/new";
        }

        topic.setCreator(userDao.get(userDao.getIdByUsername(principal.getName())));
        topicDao.add(topic, reply.getText());
        return "redirect:/";
    }

    @PostMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String addReplyForTopic(@PathVariable int id, @ModelAttribute @Valid TopicReply reply,
                                   BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("topic", topicDao.get(id));
            model.addAttribute("random", abs(random.nextInt()));
            return "topics/get";
        }

        Topic topic = new Topic();
        topic.setId(id);
        reply.setTopic(topic);
        reply.setCreator(userDao.get(userDao.getIdByUsername(principal.getName())));
        topicDao.addReply(reply);
        return "redirect:/topics/{id}";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteTopic(@PathVariable int id) {
        topicDao.delete(id);
        return "redirect:/";
    }
}
