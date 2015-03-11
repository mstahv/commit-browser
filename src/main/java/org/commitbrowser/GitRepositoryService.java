package org.commitbrowser;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.eclipse.jgit.lib.Repository;

/**
 *
 * @author Matti Tahvonen
 */
@ApplicationScoped
public class GitRepositoryService {

    private final ArrayList<Commit> commits = new ArrayList<>(20000);
    
    @Inject
    @ConfigProperty(name = "vaadin.gitdir")
    private String repo;

    @PostConstruct
    public void init() {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try {
            Repository repository = builder.setGitDir(new File(repo + "/.git"))
                    .readEnvironment() 
                    .findGitDir()
                    .build();
            
            Git git = new Git(repository);
            
            Iterable<RevCommit> log = git.log().call();
            for (RevCommit log1 : log) {
                final Commit commit = new Commit();
                commit.setCommitter(log1.getAuthorIdent().getName());
                commit.setEmail(log1.getAuthorIdent().getEmailAddress());
                commit.setId(log1.getName());
                commit.setMessage(log1.getShortMessage());
                commit.setTimestamp(log1.getAuthorIdent().getWhen());
                commits.add(commit);
            }
        } catch (IOException ex) {
            Logger.getLogger(GitRepositoryService.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (GitAPIException ex) {
            Logger.getLogger(GitRepositoryService.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public List<Commit> findAll() {
        return Collections.unmodifiableList(commits);
    }

    public int count() {
        return commits.size();
    }

    public List<Commit> find(int startindex, int maxResults) {
        int end = startindex + maxResults;
        if (end > commits.size()) {
            end = commits.size();
        }
        return new ArrayList(commits.subList(startindex, end));
    }

}
