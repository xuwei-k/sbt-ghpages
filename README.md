# xsbt-ghpages-plugin #

The github pages plugin for SBT provides support for auto-generating a project website and pushing to github pages.   Out of the box is will publish your scaladoc APIs to github pages.

## Creating ghpages branch ##

To use this pluign, you must first create a ghpages branch on github.  To do so:

        $ cd /path/to/tmpdirectory
        $ git clone {your project}
        $ cd {your project}
        $ git symbolic-ref HEAD refs/heads/gh-pages
        $ rm .git/index
        $ git clean -fdx
        $ echo "My GitHub Page" > index.html
        $ git add .
        $ git commit -a -m "First pages commit"
        $ git push origin gh-pages

Once this is done, you can begin using the plugin.

## Adding to your project ##

Create a project/plugins/project/Build.scala file that looks like the following:

    import sbt._
    object PluginDef extends Build {
      override def projects = Seq(root)
      lazy val root = Project("plugins", file(".")) dependsOn(ghpages)
      lazy val ghpages = uri("git://github.com/jsuereth/xsbt-ghpages-plugin.git")
    }

Then in your build.sbt file, simply add:


    seq(site.settings:_*)

    seq(ghpages.settings:_*)
    
    ghpages.gitRemoteRepo := "git@github.com:{your username}/{your project}.git"
    
## Creating a Home page ##

The ghpages plugin will copy anything in the target/site directory into the ghpages repository root directory.  To ensure that your site is built before things get copied, a dummy task called "gen-site" (or `genSite`) is provided.   The ghpages plugin does not care what other plugins or tasks generate a site, only that you add an appropriate dependency on that task so that it occurs *before* the site is copied to github.

For example, if you were using the sbt-lwm-plugin to generate HTML from markdown, you could use the following config:

    seq(org.clapper.sbt.lwm.LWM.lwmSettings: _*)
    
    LWM.sourceFiles in LWM.Config <++= baseDirectory(d => (d / "src" / "site" ** "*.md").get)
    
    LWM.targetDirectory in LWM.Config <<= target(_ / "site")
    
    SiteKeys.siteMappings <<= (SiteKeys.siteMappings, LWM.translate in LWM.Config, LWM.targetDirectory in LWM.Config) map { (mappings, _, dir) => 
      // TODO - less hacky solution!
      mappings ++ (dir ** "*.html" x relativeTo(dir))
    }

The `SiteKeys.siteMappings` key is a convenience reference to `mappings in SiteKeys.site`.  The ghpages plugin by default pushes every file in the mapping for `site` into the `ghpages` branch on github.  

## Publishing an API ##

By Default, the site plugin includes a mapping to place all generated API documentation under the `latest/api` directory in ghpages.

## Multi-module projects ##

The ghpages and site plugins can still be used for mutli-module projects.  In your `project/<?>.scala` file, make sure the `ghpages` settings are only included in the project which will be creating the main site.   You can use SBT's powerful setting features to modify and merge the site mappings from individual projects to construct the total project website.

TODO - Examples.





## LICENSE ##

Copyright (c) 2008, 2009, 2010, 2011 Josh Suereth, Steven Blundy, Josh Cough, Mark Harrah, Stuart Roebuck, Tony Sloane, Vesa Vilhonen, Jason Zaugg
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. The name of the author may not be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


Note:
This plugin is adapted from the SBT 0.10.x source code for general usage among projects.
