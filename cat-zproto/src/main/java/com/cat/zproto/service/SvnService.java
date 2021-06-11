package com.cat.zproto.service;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.ISVNStatusHandler;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.domain.system.SettingConfig;

@Service
public class SvnService {

	private static Logger logger = LoggerFactory.getLogger(SvnService.class);

	@Autowired
	private SettingConfig setting;

	@Autowired
	private SVNClientManager clientManager;
	
	private static String commitMessage = "协议自动提交";

	public void checkOut() {
		try {
			// svn://协议的库。 必须先执行此操作
			SVNURL repositoryURL = SVNURL.parseURIEncoded(setting.getSourceCheckOutUrl());
			// 要把版本库的内容check out到的目录
			File checkOutDir = new File(CommonConstant.GENERATOR_PATH);
			// 通过客户端管理类获得updateClient类的实例。
			SVNUpdateClient updateClient = this.clientManager.getUpdateClient();
			// sets externals not to be ignored during the checkout
			updateClient.setIgnoreExternals(false);
			// 执行check out操作，返回工作副本的版本号。
			long workingVersion = updateClient.doCheckout(repositoryURL, checkOutDir, SVNRevision.HEAD,
					SVNRevision.HEAD, SVNDepth.INFINITY, false);
			logger.info("VERSION：{} check out to {}", workingVersion, checkOutDir);
		} catch (Exception e) {
			logger.error("SvnService.doCheckOut error: ", e);
		}

	}

	/**
	 * svn更新
	 */
	public void update1() {
		try {
			// 相关变量赋值
			SVNURL.parseURIEncoded(setting.getSourceCheckOutUrl());
			// 要更新的文件
			File updateFile = new File(CommonConstant.GENERATOR_PATH);
			// 获得updateClient的实例
			SVNUpdateClient updateClient = this.clientManager.getUpdateClient();
			updateClient.setIgnoreExternals(false);
			// 执行更新操作
			long versionNum = updateClient.doUpdate(updateFile, SVNRevision.HEAD, SVNDepth.INFINITY, false, false);
			logger.info("updated version is {}", versionNum);
		} catch (Exception e) {
			logger.info(e.getMessage() + "{}", e);
		}
	}
	
	/**
	 * svn更新
	 */
	public void update(String path) {
		try {
			// 要更新的文件或目录
			File updateFile = new File(path);
			if (updateFile.isDirectory()) {
				logger.info("updated Directory is {}", updateFile.getName());
			}else if(updateFile.isFile()){
				logger.info("updated File is {}", updateFile.getName());
				try {
					SVNInfo svnInfo = clientManager.getWCClient().doInfo(updateFile, SVNRevision.HEAD);
					if(svnInfo == null) {
						return;
					}
				} catch (SVNException e) {
					logger.warn("not find note:" + updateFile.getPath());
				}
			}
			// 获得updateClient的实例
			SVNUpdateClient updateClient = this.clientManager.getUpdateClient();
			updateClient.setIgnoreExternals(false);
			// 执行更新操作
			long versionNum = updateClient.doUpdate(updateFile, SVNRevision.HEAD, SVNDepth.INFINITY, false, false);
			logger.info("updated version is {}", versionNum);
		} catch (Exception e) {
			logger.info(e.getMessage() + "{}", e);
		}
	}

	/**
	 * 更新svn
	 * @return int- 1更新失败 ， 1成功 ， 0有程序在占用更新
	 */
	public int doUpdate(String path) {
		try {
			// 要更新的文件
			File updateFile = new File(path);
			SVNInfo svnInfo = null;
			try {
				svnInfo = clientManager.getWCClient().doInfo(updateFile, SVNRevision.HEAD);
			} catch (SVNException e) {
				logger.warn("not find note:" + updateFile.getPath());
			}
			if (svnInfo == null) {
				return -1;
			}
			// 获得updateClient的实例
			SVNUpdateClient updateClient = clientManager.getUpdateClient();
			updateClient.setIgnoreExternals(false);
			// 执行更新操作
			long versionNum = updateClient.doUpdate(updateFile, SVNRevision.HEAD, SVNDepth.INFINITY, false, false);
			logger.info("svn after update version：" + versionNum);
			return 1;
		} catch (SVNException e) {
			logger.error("svn update[{}] error", path, e);
			return -1;
		} finally {
		}
	}

	public void commit(String filePath) {
		try {
			// 相关变量赋值
//			SVNURL.parseURIEncoded(setting.getSourceCheckOutUrl());
			// 要提交的文件
			File commitFile = new File(filePath);
			// 获取此文件的状态（是文件做了修改还是新添加的文件？）
			SVNStatus status = this.clientManager.getStatusClient().doStatus(commitFile, true);
			// 如果此文件是新增加的则先把此文件添加到版本库，然后提交。
			if (status.getContentsStatus() == SVNStatusType.STATUS_UNVERSIONED) {
				// 把此文件增加到版本库中
				this.clientManager.getWCClient().doAdd(commitFile, false, false, false, SVNDepth.INFINITY, false,
						false);
				// 提交此文件
				this.clientManager.getCommitClient().doCommit(new File[] { commitFile }, true, commitMessage, null, null,
						true, false, SVNDepth.INFINITY);
				System.out.println("update");
			}
			// 如果此文件不是新增加的，直接提交。
			else {
				this.clientManager.getCommitClient().doCommit(new File[] { commitFile }, true, commitMessage, null, null,
						true, false, SVNDepth.INFINITY);
				System.out.println("commit");
			}
			logger.info("svn commit[{}]", commitFile.getName());
		} catch (Exception e) {
			logger.error("commit error, {}", e);
		}
	}

	/**
	 * Svn提交
	 * 
	 * @param filePath      文件相对路径
	 * @param commitMessage 提交信息
	 * @return
	 */
	public boolean doCommit(String dirPath) {
		if (StringUtils.isEmpty(dirPath)) {
			return true;
		}
		try {
			File dir = new File(dirPath);
			/*
			 * 文件过滤器
			 * 1. 文件不能为隐藏文件
			 * 2.文件夹不能为隐藏, 必须为文件夹, 名字不能包含download
			 */
			IOFileFilter fileFilter = FileFilterUtils.makeSVNAware(HiddenFileFilter.VISIBLE);
			IOFileFilter dirFilter = FileFilterUtils.makeSVNAware(FileFilterUtils.and(FileFilterUtils.directoryFileFilter() 
							,HiddenFileFilter.VISIBLE
			    		  //,FileFilterUtils.notFileFilter(FileFilterUtils.nameFileFilter("download"))
			    		  ));
			Collection<File> collFiless = FileUtils.listFilesAndDirs(dir, fileFilter, dirFilter);
			File[] files = collFiless.toArray(new File[collFiless.size()]);
			for (File file : files) {
				 clientManager.getStatusClient().doStatus(file, SVNRevision.HEAD, SVNDepth.INFINITY, false, false, false, false, new ISVNStatusHandler() {
	                    @Override
	                    public void handleStatus(SVNStatus status) throws SVNException {
	                        if (SVNStatusType.STATUS_UNVERSIONED.equals(status.getNodeStatus())) {
	                        	clientManager.getWCClient().doAdd(status.getFile(), true, false, false, SVNDepth.EMPTY, false, false);
	                        } else if (SVNStatusType.STATUS_MISSING.equals(status.getNodeStatus())) {
	                        	clientManager.getWCClient().doDelete(status.getFile(), true, false, false);
	                        }
	                    }
	                }, null);
	            }
			// 提交此文件
			clientManager.getCommitClient().doCommit(files, true, commitMessage, null, null, true, false, SVNDepth.INFINITY);
			logger.info("svn commit file[{}]", dir.getPath());
		} catch (Exception e) {
			logger.error("doCommit error, {}", e);
		}
		return false;
	}
	
	  /**
     * 解除svn Luck
     *
     * @param path
     * @return
     */
    public boolean doCleanup(String path) {
        // 要把版本库的内容check out到的目录
        File wcDir = new File(path);
        if (wcDir.exists()) {
            try {
            	clientManager.getWCClient().doCleanup(wcDir);
            	logger.error("svn doCleanup success");
            } catch (SVNException e) {
                logger.error("svn cleanup[{}] error", path, e);
                return false;
            }
        } else {
        	 logger.error("svn doCleanup, path is not found");
            return false;
        }
        return true;
    }
	
}