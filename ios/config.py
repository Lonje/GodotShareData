def can_build(env, platform):
	return platform=="iphone"
#	return platform=="android" or platform=="iphone"

def configure(env):
#	if (env['platform'] == 'android'):
#		env.android_add_java_dir("android")
#		env.android_add_to_manifest("android/AndroidManifestChunk.xml")
#		#uncomment this line to godot <= 3.0.6
#		#env.android_add_dependency("compile 'androidx.core:core:1.2.0'")
#		env.android_add_res_dir("android/res")
#		env.disable_module()

	if env['platform'] == "iphone":
		env.Append(LINKFLAGS=['-ObjC'])
		env.Append(CPPPATH=['#core'])
