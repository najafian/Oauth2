package com.helia.oauth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.core.io.Resource
import org.springframework.util.Base64Utils
import org.springframework.util.StreamUtils
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.charset.StandardCharsets
import java.security.*
import java.security.interfaces.RSAPublicKey
import java.security.spec.EncodedKeySpec
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
class JwtProperties(privateKey: Resource, publicKey: Resource) {
    val keyPair: KeyPair

    init {
        keyPair = KeyPair(resourceToPublicKey(publicKey), resourceToPrivateKey(privateKey))
    }

    companion object {
        @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
        private fun resourceToPublicKey(resource: Resource): PublicKey {
            val key = resourceToString(resource)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .trim { it <= ' ' }
                .replace("\r\n", "")
                .replace("\n", "")
            val decode = Base64Utils.decodeFromString(key)
            val keySpec = X509EncodedKeySpec(decode)
            val kf = KeyFactory.getInstance("RSA")
            return kf.generatePublic(keySpec) as RSAPublicKey
        }

        @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
        private fun resourceToPrivateKey(resource: Resource): PrivateKey {
            val key = resourceToString(resource)
            val decoded = Base64Utils.decodeFromString(key
                .replace("fake", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .trim { it <= ' ' }
                .replace("\r\n", "")
                .replace("\n", ""))
            val keySpec: EncodedKeySpec = PKCS8EncodedKeySpec(decoded)
            val kf = KeyFactory.getInstance("RSA")
            return kf.generatePrivate(keySpec)
        }

        private fun resourceToString(resource: Resource): String {
            try {
                resource.inputStream.use { stream -> return StreamUtils.copyToString(stream, StandardCharsets.UTF_8) }
            } catch (e: IOException) {
                throw UncheckedIOException(e)
            }
        }
    }
}