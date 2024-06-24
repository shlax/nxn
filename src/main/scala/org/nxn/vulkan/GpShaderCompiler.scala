package org.nxn.vulkan

import org.lwjgl.util.shaderc.Shaderc

import scala.io.Source
import org.nxn.Extensions.*

class GpShaderCompiler extends AutoCloseable{

  val compiler:Long = Shaderc.shaderc_compiler_initialize()
  val options: Long = Shaderc.shaderc_compile_options_initialize()

  def compile(uri:String, shaderType:Int, entryPoint:String = "main"): Array[Byte] = {
    val src = this.getClass.getResourceAsStream(uri)|{ in =>
      Source.fromInputStream(in, "UTF-8").getLines().mkString("\n")
    }
    val res = Shaderc.shaderc_compile_into_spv(compiler, src, shaderType, uri, entryPoint, options)
    if (Shaderc.shaderc_result_get_compilation_status(res) != Shaderc.shaderc_compilation_status_success) {
      throw new RuntimeException("Shader compilation failed: " + Shaderc.shaderc_result_get_error_message(res))
    }

    val buff = Shaderc.shaderc_result_get_bytes(res)
    val compiled = new Array[Byte](buff.remaining())
    buff.get(compiled)

    compiled
  }

  override def close(): Unit = {
    Shaderc.shaderc_compile_options_release(options)
    Shaderc.shaderc_compiler_release(compiler)
  }

}
