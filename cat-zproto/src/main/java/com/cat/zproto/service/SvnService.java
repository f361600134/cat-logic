package com.cat.zproto.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.*;

import com.cat.zproto.domain.system.SettingConfig;

import java.io.File;

@Service
public class SvnService {
 
	
//	@Autowired 
//	private SettingConfig setting;
//	
//	@Autowired 
//    private SVNClientManager clientManager;
// 
//    public void checkOut() {
//        final String user = config.getSourceSvnUser();
//        final String password = config.getSourceSvnPassword();
//        final String sourceSvn = config.getSourceSvn() + config.getSourceProject();
//        try {
//            //初始化支持svn://协议的库。 必须先执行此操作。
//            SVNRepositoryFactoryImpl.setup();
// 
//            //相关变量赋值
//            SVNURL repositoryURL = SVNURL.parseURIEncoded(sourceSvn);
// 
//            ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
// 
// 
//            //实例化客户端管理类
//            this.clientManager = SVNClientManager.newInstance(
//                    (DefaultSVNOptions) options, user, password);
// 
//            //要把版本库的内容check out到的目录
//            File wcDir = new File(config.getSourceCheckOutDir());
// 
//            //通过客户端管理类获得updateClient类的实例。
//            SVNUpdateClient updateClient = this.clientManager.getUpdateClient();
// 
//            // sets externals not to be ignored during the checkout
//            updateClient.setIgnoreExternals(false);
// 
// 
//            //执行check out操作，返回工作副本的版本号。
//            long workingVersion = updateClient.doCheckout(
//                    repositoryURL, wcDir,
//                    SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY,
//                    false);
// 
// 
//            log.info("VERSION：{} check out to {}", workingVersion, wcDir);
//        } catch (Exception e) {
//            log.error("SvnService.doCheckOut error: ", e);
//        }
// 
//    }
// 
//    public void update() {
//        final String user = config.getSourceSvnUser();
//        final String password = config.getSourceSvnPassword();
//        final String sourceSvn = config.getSourceSvn() + config.getSourceProject();
//        try {
//            //初始化支持svn://协议的库。 必须先执行此操作。
//            SVNRepositoryFactoryImpl.setup();
// 
//            //相关变量赋值
//            SVNURL.parseURIEncoded(sourceSvn);
// 
//            ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
// 
//            //实例化客户端管理类
//            this.clientManager = SVNClientManager.newInstance(
//                    (DefaultSVNOptions) options, user, password);
// 
//            //要更新的文件
//            File updateFile = new File(config.getSourceCheckOutDir());
// 
//            //获得updateClient的实例
//            SVNUpdateClient updateClient = this.clientManager.getUpdateClient();
// 
//            updateClient.setIgnoreExternals(false);
// 
//            //执行更新操作
//            long versionNum = updateClient.doUpdate(updateFile, SVNRevision.HEAD, SVNDepth.INFINITY, false, false);
// 
//            log.info("updated version is {}", versionNum);
//        } catch (Exception e) {
//            log.info(e.getMessage() + "{}", e);
//        }
//    }
// 
//    public void commit() {
//        final String user = config.getSourceSvnUser();
//        final String password = config.getSourceSvnPassword();
//        final String sourceSvn = config.getSourceSvn() + config.getSourceProject();
//        try {
//            //初始化支持svn://协议的库。 必须先执行此操作。
// 
//            SVNRepositoryFactoryImpl.setup();
// 
//            //相关变量赋值
// 
//            SVNURL.parseURIEncoded(sourceSvn);
// 
// 
//            ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
// 
//            //实例化客户端管理类
// 
//            this.clientManager = SVNClientManager.newInstance(
// 
//                    (DefaultSVNOptions) options, user, password);
// 
//            //要提交的文件
// 
//            File commitFile = new File(config.getSourceCheckOutDir());
// 
//            //获取此文件的状态（是文件做了修改还是新添加的文件？）
// 
//            SVNStatus status = this.clientManager.getStatusClient().doStatus(commitFile, true);
// 
//            //如果此文件是新增加的则先把此文件添加到版本库，然后提交。
// 
//            if (status.getContentsStatus() == SVNStatusType.STATUS_UNVERSIONED) {
// 
//                //把此文件增加到版本库中
// 
//                this.clientManager.getWCClient().doAdd(commitFile, false, false, false, SVNDepth.INFINITY, false, false);
// 
//                //提交此文件
// 
//                this.clientManager.getCommitClient().doCommit(
// 
//                        new File[]{commitFile}, true, "", null, null, true, false, SVNDepth.INFINITY);
// 
//                System.out.println("add");
// 
//            }
// 
//            //如果此文件不是新增加的，直接提交。
// 
//            else {
// 
// 
//                this.clientManager.getCommitClient().doCommit(
// 
//                        new File[]{commitFile}, true, "", null, null, true, false, SVNDepth.INFINITY);
// 
//                System.out.println("commit");
// 
//            }
// 
//            System.out.println(status.getContentsStatus());
//        } catch (Exception e) {
//            log.error(e.getMessage() + "{}", e);
//        }
//    }
}