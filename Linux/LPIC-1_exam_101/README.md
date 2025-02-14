# LPIC-1 exam 101 preparation and fast learning guide

# Copyright

Copyright (C)  2025 André Schepers.
Permission is granted to copy, distribute and/or modify this document
under the terms of the GNU Free Documentation License, Version 1.3
or any later version published by the Free Software Foundation;
with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
A copy of the license is included in the section entitled "GNU
Free Documentation License".

# What is it

https://www.lpi.org/our-certifications/lpic-1-overview/

# Introduction

This material follows the
book: [LPIC-1 study guide](https://www.oreilly.com/library/view/lpic-1-linux-professional/9781119582120/).

It tries to give a study guide, to easily study the material covered in the
book. It uses
the [Hypothesis of fast Learning](https://github.com/andres81/Model_of_the_Psyche/tree/main/model-of-the-psyche#appendix-a---hypothesis-of-fast-learning).

The instruction how to use this guide is:

* Look at the questions, and try to answer them.
* If you cannot answer them, look at the answers.
* Go back to the questions try to answer them yourself
* Try to do this while you do other things, like sporting, traveling, etc...

The idea is, that the exercise of answering the questions, will become a mental
background process. This way you don't spent energy on it actively, but
passively
and using energy from much bigger energy source.

Part of this methodology is of course, using the act of reproducing, instead of
recognising new material to memorise by heart. Only the act of (trying to)
reproducing will make you learn new material for life in combination with
repitition. Repeating the material is a choice to do so by the reader, the
material offers a way to help you reproduce the material to learn by giving
question answer combinations.

# Chapter 1

## 1.1 Understanding Command-Line Basics

## 1.2 Editing Text Files

## 1.3 Processing Text Using Filters

### File-Combining Commands

* cat
    * Often used to display file contents on the screen, although its primary
      use is concatenating contents of files.
    * To concatenate:
        * cat file1.txt file2.txt
    * Commonly used options:
        * -A (--show-all): Equivalent to -vET
        * -E (--show-ends): $ in case of newline linefeed
        * -n (--number): number file lines
        * -s (--squeeze-blank): do not repeat blank lines
        * -T (--show-tabs): ^I in case of a tab
        * -v (--show-nonprinting): non printing characters
* paste: file contents as two columns next to each other

### File-Transforming Commands

#### Uncovering with *od*

* od: shows the files text in octal by default.
    * Can show it in octal, hexa, decimal and ASCII
    * With the -cb option, the characters are also shown

#### Separating with *split*

* Splits a file by line count or other means.
* Will create new files with suffixes, starting at aa, ab, ac... so forth.

### File-Formatting Commands

#### Organizing with *sort*

* sort file1.txt: Will output the sorted contents of the file, but the file
  remains untouched.
* sort -n file_with_numbers2.txt: Will properly order entries that are numbers.
* sort -o outputfile.txt file3.txt: Will sort the contents of file3.txt and
  store it in outputfile.txt

#### Numbering with *nl*

* nl file1.txt: Will number all non-blank lines
* nl -ba file2.txt: Will number all, including blank, lines.

### File-Viewing Commands

#### Using *more* or *less*

* *more* and *less* both are pagers
* *less* has more functionality, and reads faster because it does not read the
  entire file but page by page.

#### Looking at fiels with *head*

* *head* command shows by default the first 10 lines of a file
* Override with -n or just -*n*:
    * head -n 15 file1.txt
    * head -15 file2.txt

#### Viewing Files with *tail*

* By default, last 10 lines
* Override like with head
* Use the + sign to start displaying from that line to the end
    * tail n, +42 fileendsonlyplease.txt
* Use -f to follow the file, handy with log files.
    * tail -f somepoorlogfilebeingwatched.txt

### File-Summarizing Commands

#### Counting with *wc*

* wc file, will show the files number of lines, words and bytes.
*

## 1.4 Using Regular Expressions

## 1.5 Using Streams, Rediction, and Pipes

# Chapter 2

# Chapter 3

# Chapter 4

# Chapter 5 - Booting, Initializing, and Virtualizing Linux

## 5.1 Understanding the Boot Process

### The Boot Process

* Firmware starts: Power On Self Test (POST)
* Bootloader runs: Determines what OS to start (Linux: Which kernel to start)
* Kernel loads, mounts the root partition, starts the init process (all
  services: PID 1 is the init program)
* The init process loads necessary services, like the graphical.target: Desktop
  manager, daemons and everything else to be able to use the computer as a
  normal
  civilian.

#### Questions:

1. How many steps are there to start a Linux System? Which are they?
2. If the kernel is missing, or there is a fault in the kernel and therefore it
   cannot start, between which steps will the startup process halt?
3. If a hacker deleted the initialization process software, SystemD for example,
   when does it go wrong, after which step?
4. If there is a hardware problem, faulty RAM, between what steps does the
   startup process go wrong?

#### Answers:

1. 4
    1. Firmware starts, does a Power On Self Test
    2. Bootloader starts, presents a menu to let the user choose a kernel to
       start, or immediately starts a kernel
    3. Kernel starts, inits the hardware, and starts the init process
    4. The init program/process starts (PID 1) and loads all system services,
       auxiliary services and any other service that needs loaded. Daemons are
       example services.
2. If the kernel is missing, the bootloader cannot load a kernel, so this is
   during step 2 and in between 2 and 3.
   If the kernel starts, but crashes/stops due to an error in the kernel, the
   process stops during step 3.
3. After step 3, start of the kernel, and during step 4, the running of the init
   process.
4. During step 1, the firmware starting process and the POST, the test of the
   hardware.

### Extracting Information about the Boot Process

* During the startup process, Linux will dump info regarding this process to the
  terminal screen.
* All this info is also stored in a special ring buffer in memory, accessible
  after startup with the dmesg command. • Also available with the journalctl
  command, if that tool is available on the system. It is part of the SystemD
  suite.
* Some Linux distros have a log file in the /var/log directory:
    * Debian: /var/log/boot
    * Red Hat: /var/log/boot.log
* If a system utilises systemd-journald then the logs are stored in a journal
  file.

#### Questions:

1. What command can be used to read the ring buffer with logs of the Linux
   startup process?
2. What tool can otherwise be used to read these log messages? What tool needs
   to be installed and what is this tool part of?
3. Are there other places where a log file can exist?
    1. What distros?
    2. What names are used for these files?

#### Answers:

1. Dmesg
2. Journalctl, part of the SystemD suite. The system is: systemd-journald.
3. Two:
    1. Debian: /var/log/boot
    2. Red Hat: /var/log/boot.log

## 5.2 Looking at Firmware

### The BIOS Startup

* BIOS can only read one sector worth of data from the harddisk
* Loads a bootloader
* Bootloader shows a small menu or loads a kernel immediately.
* Loads the necessary hardware to start the OS
* Necessary because one sector is not enough data space to load an entire OS,
  therefore two step process
* Bootloader starts and knows where to find the Linux kernel to start it

#### Questions:

1. What is a limitation of the BIOS?
2. What is the solution to the limitation of the BIOS?
3. Where does BIOS find the next system that continues the boot process?
4. What is the name of the data section where the next system resides?
5. What is the name of the next piece of software that runs after BIOS?

#### Answers:

1. Can only load the first sector of the first partition of a hard drive
2. Let the Linux OS boot in two steps:
1. First a little piece of software in the Master Boot Record (MBR) section of
   the disk, the first sector of the first partition of the disk.
2. The second software is the kernel that bootstraps the hardware and will after
   its done as a final step start the init process, the final step of starting a
   Linux system.
3. MBR
4. Master Boot Record
5. Bootloader

### The UEFI Startup

* UEFI: Universal Extensible Firmware Interface
* Utilizes special disk partition to startup: EFI System Partition
* Uses File Allocation Table (FAT) format filesystem.
* Typically the ESP is mounted in Linux under /boot/efi
* Boot files .efi extension
* UEFI firmware uses built-in mini boot loader: Boot manager

#### Questions:

1. How does the startup differ of the UEFI with BIOS?
2. What does UEFI stand for?
3. What is the ESP? What is it for?
4. What is the extension of the files used for EFI boot?
5. Where is the ESP typically mounted under Linux?

#### Answers:

1. UEFI defines a boot partition, instead of looking at the first sector of the
   harddisk
2. Universal Extensible Firmware Interface
3. Efi System Partition. The partition that holds the boot files for particular
   boots.
4. .efi
5. /boot/efi

## 5.3 Looking at Boot Loaders

### Grub Legacy

* Uses a menu and interactive shell to let the user choose a OS to start
* Stores the menu commands and settings of this menu in a config file: menu.lst,
  in the /boot/grub/ directory. Red Hat distros store it in grub.conf instead of
  menu.lst
* menu.lst has two sections:
    * Global definitions (Must appear first, concern overall operation of the
      menu)
    * Operating system boot definitions
* Settings global (6):
    * color: Fore- and background colors in the menu (selected and unselected
      items)
    * default: Default menu option selected
    * fallback: Fallback if default fails
    * hiddenmenu: Don't display the menu to choose a OS
    * splashimage: Points to image that forms the background
    * timeout: Waiting time before the default is chosen
* Settings individual menu items (5):
    * title: Menu item listing title
    * root: Disk and partition where /boot folder partition is located (
      format: (hd0,0), first disk, first partition).
    * kernel: Where the kernel image file lives in the /boot folder.
    * initrd: Defines initial RAM disk file or filesystem, necessary drivers for
      kernel to talk to hardware. Used for complex hardware, for which the
      kernel doesn't have compiled into it
    * rootnoverify: Windows OS
* Installing Grub Legacy:
    * grub-install /dev/sda
    * grub-install 'hd(0,0)'

#### Questions:

1. what is Grub Legacy?
2. What is the location of the Grub Legacy config file?
3. How is the config file build up?
4. How many options to configure are there in the config file? What are they?
5. How to install Grub Legacy?

#### Answers:

1. A Boot Loader, that offers in addition to laoding an OS, a menu to choose a
   OS
2. Two locations:
    1. /boot/grub/menu.lst
    2. /boot/grub/grub.conf on Red Hat systems
3. Two sections
    1. Menu configuration (color, default option, timeout etc...)
    2. Menu entries, each one representing an OS that can be loaded
4. Depending on the menu section:
    1. Menu config, (6) options:
        1. color
        2. default
        3. fallback
        4. hiddenmenu
        5. splashimage
        6. timeout
    2. Menu items, (5) options:
        1. title
        2. root
        3. kernel
        4. initrd
        5. rootnoverify
5. grub-install /dev/sda or grub-install hd(0,0)

### Using GRUB 2 as the Boot Loader

* Different place and name than Grub Legacy for the config file
    * BIOS: /boot/grub/grub.cfg or /boot/grub2/grub.cfg
    * UEIF: /boot/efi/EFI/distro-name/grub.cfg
* Config file uses different numbering:
    * starting at 0 for hardrive
    * starting with 1 for partitions
* Config file uses set command
* Commands used:
    * menuentry:
    * set root
    * linux, linux16
    * linuxefi
    * initrd
    * initrdefi
    * Example:

```
menuentry "CentOS Linux" {
    set root=(hd1,1)
    linux16 /vmlinuz
    initrd /initramfs
}
```

* Non-linux OSes are defined the same as Linux
* Never use /boot/grub/grub.cfg, but instead the config files under /etc/grub.d
* The global config for Grub2 can be found at /etc/default/grub, but also never
  edit that one either: /etc/grub.d instead
* No need to explicitly install Grub2 like Grub Legacy, only rebuild the main
  installation file.
* Update using: grub2-mkconfig > <target file>, Ubuntu: update-grub utility

## 5.4 The Initialization Process

* Two initialization daemons you need to know:
    * SysVinit
    * Systemd
* Service startups classically handled by the init program, found in one of the
  directories:
    * /etc/
    * /bin/
    * /sbin/
* To find out what system is used:
    * Command: "which init", f.e.: /sbin/init, then: "readlink -f /sbin/init",
      /usr/lib/systemd/systemd
    * Check PID (proess ID): "ps -p 1"

## 5.5 Using the systemd Initialization Process

* Services can be started when:
    * The system boots
    * When a particular hardware is attached
    * When particular other services are started

### Exploring Unit Files

* A unit defines:
    * A service
    * A group of services
    * An action
* Each unit consists of:
    * A name
    * A type
    * A configuration file
* In total now 12 different system unit types
* To manage services: systemctl
    * Example, to get a list of loaded units: systemctl list-units
* Groups of services are started via target unit files. Examples are:
    * graphical.target
    * multi-user.target
    * runleveln.target
* Master systemd config file: /etc/systemd/system.conf. Advise is to use the man
  page man systemd-system.conf

### Focusing on Service Unit Files

* Service unit files contain information regarding:
    * which environment file to use
    * when it should be started
    * what targets want this service started
* Unit files are located in separate directories
* The directory location of a unit file is critical: Determines precedence order
* Ascending priority order of directories in which unit files are found:
    * /etc/systemd/system/
    * /run/systemd/system/
    * /usr/lib/systemd/system/
* List available unit files: systemctl list-unit-files
* 12 different enablement states exist, three are:
    * enabled: will start at system boot
    * disabled: not start at system boot
    * static: Only started if another unit depends on it
* Finding a unit file is done by showing the contents of the file, the first
  line will show its location: systemctl cat ntpd.service
* Three primary configuration sections in unit files:
    * [Unit]: Containing basic directives (7 fields)
    * [Service]: Configuration items specific to that service (8 fields)
    * [Install]: Describes what happens with a unit when it is enabled or
      disabled (4 fields)
        * Alias: Sets additional names that can denote the service in systemctl
        * Also: Additional units required to be enabled or disabled
        * RequiredBy: Other units that need this service
        * WantedBy: Which target unit manages this service
* For information:
    * man -k systemd
    * man systemd.directives

### Focusing on Target Unit Files

* Primary purpose of target unit files is to group together various services to
  start at system boot time.
* The default target file, default.target, is symbolically linked to the target
  unit file used at system boot time.
* Get the default: systemctl get-default
* Show that the target file encompasses: systemctl cat graphical.target

### Looking at *systemctl*

* Systemctl command
    * status: Show status of a service (disabled/enabled, running/inactive)
        * systemctl status ntpd
        * systemctl status sshd

# GNU Free Documentation License

Version 1.3, 3 November 2008

Copyright (C) 2000, 2001, 2002, 2007, 2008 Free Software Foundation,
Inc. <https://fsf.org/>

Everyone is permitted to copy and distribute verbatim copies of this
license document, but changing it is not allowed.

## 0. PREAMBLE

The purpose of this License is to make a manual, textbook, or other
functional and useful document "free" in the sense of freedom: to
assure everyone the effective freedom to copy and redistribute it,
with or without modifying it, either commercially or noncommercially.
Secondarily, this License preserves for the author and publisher a way
to get credit for their work, while not being considered responsible
for modifications made by others.

This License is a kind of "copyleft", which means that derivative
works of the document must themselves be free in the same sense. It
complements the GNU General Public License, which is a copyleft
license designed for free software.

We have designed this License in order to use it for manuals for free
software, because free software needs free documentation: a free
program should come with manuals providing the same freedoms that the
software does. But this License is not limited to software manuals; it
can be used for any textual work, regardless of subject matter or
whether it is published as a printed book. We recommend this License
principally for works whose purpose is instruction or reference.

## 1. APPLICABILITY AND DEFINITIONS

This License applies to any manual or other work, in any medium, that
contains a notice placed by the copyright holder saying it can be
distributed under the terms of this License. Such a notice grants a
world-wide, royalty-free license, unlimited in duration, to use that
work under the conditions stated herein. The "Document", below, refers
to any such manual or work. Any member of the public is a licensee,
and is addressed as "you". You accept the license if you copy, modify
or distribute the work in a way requiring permission under copyright
law.

A "Modified Version" of the Document means any work containing the
Document or a portion of it, either copied verbatim, or with
modifications and/or translated into another language.

A "Secondary Section" is a named appendix or a front-matter section of
the Document that deals exclusively with the relationship of the
publishers or authors of the Document to the Document's overall
subject (or to related matters) and contains nothing that could fall
directly within that overall subject. (Thus, if the Document is in
part a textbook of mathematics, a Secondary Section may not explain
any mathematics.) The relationship could be a matter of historical
connection with the subject or with related matters, or of legal,
commercial, philosophical, ethical or political position regarding
them.

The "Invariant Sections" are certain Secondary Sections whose titles
are designated, as being those of Invariant Sections, in the notice
that says that the Document is released under this License. If a
section does not fit the above definition of Secondary then it is not
allowed to be designated as Invariant. The Document may contain zero
Invariant Sections. If the Document does not identify any Invariant
Sections then there are none.

The "Cover Texts" are certain short passages of text that are listed,
as Front-Cover Texts or Back-Cover Texts, in the notice that says that
the Document is released under this License. A Front-Cover Text may be
at most 5 words, and a Back-Cover Text may be at most 25 words.

A "Transparent" copy of the Document means a machine-readable copy,
represented in a format whose specification is available to the
general public, that is suitable for revising the document
straightforwardly with generic text editors or (for images composed of
pixels) generic paint programs or (for drawings) some widely available
drawing editor, and that is suitable for input to text formatters or
for automatic translation to a variety of formats suitable for input
to text formatters. A copy made in an otherwise Transparent file
format whose markup, or absence of markup, has been arranged to thwart
or discourage subsequent modification by readers is not Transparent.
An image format is not Transparent if used for any substantial amount
of text. A copy that is not "Transparent" is called "Opaque".

Examples of suitable formats for Transparent copies include plain
ASCII without markup, Texinfo input format, LaTeX input format, SGML
or XML using a publicly available DTD, and standard-conforming simple
HTML, PostScript or PDF designed for human modification. Examples of
transparent image formats include PNG, XCF and JPG. Opaque formats
include proprietary formats that can be read and edited only by
proprietary word processors, SGML or XML for which the DTD and/or
processing tools are not generally available, and the
machine-generated HTML, PostScript or PDF produced by some word
processors for output purposes only.

The "Title Page" means, for a printed book, the title page itself,
plus such following pages as are needed to hold, legibly, the material
this License requires to appear in the title page. For works in
formats which do not have any title page as such, "Title Page" means
the text near the most prominent appearance of the work's title,
preceding the beginning of the body of the text.

The "publisher" means any person or entity that distributes copies of
the Document to the public.

A section "Entitled XYZ" means a named subunit of the Document whose
title either is precisely XYZ or contains XYZ in parentheses following
text that translates XYZ in another language. (Here XYZ stands for a
specific section name mentioned below, such as "Acknowledgements",
"Dedications", "Endorsements", or "History".) To "Preserve the Title"
of such a section when you modify the Document means that it remains a
section "Entitled XYZ" according to this definition.

The Document may include Warranty Disclaimers next to the notice which
states that this License applies to the Document. These Warranty
Disclaimers are considered to be included by reference in this
License, but only as regards disclaiming warranties: any other
implication that these Warranty Disclaimers may have is void and has
no effect on the meaning of this License.

## 2. VERBATIM COPYING

You may copy and distribute the Document in any medium, either
commercially or noncommercially, provided that this License, the
copyright notices, and the license notice saying this License applies
to the Document are reproduced in all copies, and that you add no
other conditions whatsoever to those of this License. You may not use
technical measures to obstruct or control the reading or further
copying of the copies you make or distribute. However, you may accept
compensation in exchange for copies. If you distribute a large enough
number of copies you must also follow the conditions in section 3.

You may also lend copies, under the same conditions stated above, and
you may publicly display copies.

## 3. COPYING IN QUANTITY

If you publish printed copies (or copies in media that commonly have
printed covers) of the Document, numbering more than 100, and the
Document's license notice requires Cover Texts, you must enclose the
copies in covers that carry, clearly and legibly, all these Cover
Texts: Front-Cover Texts on the front cover, and Back-Cover Texts on
the back cover. Both covers must also clearly and legibly identify you
as the publisher of these copies. The front cover must present the
full title with all words of the title equally prominent and visible.
You may add other material on the covers in addition. Copying with
changes limited to the covers, as long as they preserve the title of
the Document and satisfy these conditions, can be treated as verbatim
copying in other respects.

If the required texts for either cover are too voluminous to fit
legibly, you should put the first ones listed (as many as fit
reasonably) on the actual cover, and continue the rest onto adjacent
pages.

If you publish or distribute Opaque copies of the Document numbering
more than 100, you must either include a machine-readable Transparent
copy along with each Opaque copy, or state in or with each Opaque copy
a computer-network location from which the general network-using
public has access to download using public-standard network protocols
a complete Transparent copy of the Document, free of added material.
If you use the latter option, you must take reasonably prudent steps,
when you begin distribution of Opaque copies in quantity, to ensure
that this Transparent copy will remain thus accessible at the stated
location until at least one year after the last time you distribute an
Opaque copy (directly or through your agents or retailers) of that
edition to the public.

It is requested, but not required, that you contact the authors of the
Document well before redistributing any large number of copies, to
give them a chance to provide you with an updated version of the
Document.

## 4. MODIFICATIONS

You may copy and distribute a Modified Version of the Document under
the conditions of sections 2 and 3 above, provided that you release
the Modified Version under precisely this License, with the Modified
Version filling the role of the Document, thus licensing distribution
and modification of the Modified Version to whoever possesses a copy
of it. In addition, you must do these things in the Modified Version:

- A. Use in the Title Page (and on the covers, if any) a title
  distinct from that of the Document, and from those of previous
  versions (which should, if there were any, be listed in the
  History section of the Document). You may use the same title as a
  previous version if the original publisher of that version
  gives permission.
- B. List on the Title Page, as authors, one or more persons or
  entities responsible for authorship of the modifications in the
  Modified Version, together with at least five of the principal
  authors of the Document (all of its principal authors, if it has
  fewer than five), unless they release you from this requirement.
- C. State on the Title page the name of the publisher of the
  Modified Version, as the publisher.
- D. Preserve all the copyright notices of the Document.
- E. Add an appropriate copyright notice for your modifications
  adjacent to the other copyright notices.
- F. Include, immediately after the copyright notices, a license
  notice giving the public permission to use the Modified Version
  under the terms of this License, in the form shown in the
  Addendum below.
- G. Preserve in that license notice the full lists of Invariant
  Sections and required Cover Texts given in the Document's
  license notice.
- H. Include an unaltered copy of this License.
- I. Preserve the section Entitled "History", Preserve its Title,
  and add to it an item stating at least the title, year, new
  authors, and publisher of the Modified Version as given on the
  Title Page. If there is no section Entitled "History" in the
  Document, create one stating the title, year, authors, and
  publisher of the Document as given on its Title Page, then add an
  item describing the Modified Version as stated in the
  previous sentence.
- J. Preserve the network location, if any, given in the Document
  for public access to a Transparent copy of the Document, and
  likewise the network locations given in the Document for previous
  versions it was based on. These may be placed in the "History"
  section. You may omit a network location for a work that was
  published at least four years before the Document itself, or if
  the original publisher of the version it refers to
  gives permission.
- K. For any section Entitled "Acknowledgements" or "Dedications",
  Preserve the Title of the section, and preserve in the section all
  the substance and tone of each of the contributor acknowledgements
  and/or dedications given therein.
- L. Preserve all the Invariant Sections of the Document, unaltered
  in their text and in their titles. Section numbers or the
  equivalent are not considered part of the section titles.
- M. Delete any section Entitled "Endorsements". Such a section may
  not be included in the Modified Version.
- N. Do not retitle any existing section to be Entitled
  "Endorsements" or to conflict in title with any Invariant Section.
- O. Preserve any Warranty Disclaimers.

If the Modified Version includes new front-matter sections or
appendices that qualify as Secondary Sections and contain no material
copied from the Document, you may at your option designate some or all
of these sections as invariant. To do this, add their titles to the
list of Invariant Sections in the Modified Version's license notice.
These titles must be distinct from any other section titles.

You may add a section Entitled "Endorsements", provided it contains
nothing but endorsements of your Modified Version by various
partiesâ€”for example, statements of peer review or that the text has
been approved by an organization as the authoritative definition of a
standard.

You may add a passage of up to five words as a Front-Cover Text, and a
passage of up to 25 words as a Back-Cover Text, to the end of the list
of Cover Texts in the Modified Version. Only one passage of
Front-Cover Text and one of Back-Cover Text may be added by (or
through arrangements made by) any one entity. If the Document already
includes a cover text for the same cover, previously added by you or
by arrangement made by the same entity you are acting on behalf of,
you may not add another; but you may replace the old one, on explicit
permission from the previous publisher that added the old one.

The author(s) and publisher(s) of the Document do not by this License
give permission to use their names for publicity for or to assert or
imply endorsement of any Modified Version.

## 5. COMBINING DOCUMENTS

You may combine the Document with other documents released under this
License, under the terms defined in section 4 above for modified
versions, provided that you include in the combination all of the
Invariant Sections of all of the original documents, unmodified, and
list them all as Invariant Sections of your combined work in its
license notice, and that you preserve all their Warranty Disclaimers.

The combined work need only contain one copy of this License, and
multiple identical Invariant Sections may be replaced with a single
copy. If there are multiple Invariant Sections with the same name but
different contents, make the title of each such section unique by
adding at the end of it, in parentheses, the name of the original
author or publisher of that section if known, or else a unique number.
Make the same adjustment to the section titles in the list of
Invariant Sections in the license notice of the combined work.

In the combination, you must combine any sections Entitled "History"
in the various original documents, forming one section Entitled
"History"; likewise combine any sections Entitled "Acknowledgements",
and any sections Entitled "Dedications". You must delete all sections
Entitled "Endorsements".

## 6. COLLECTIONS OF DOCUMENTS

You may make a collection consisting of the Document and other
documents released under this License, and replace the individual
copies of this License in the various documents with a single copy
that is included in the collection, provided that you follow the rules
of this License for verbatim copying of each of the documents in all
other respects.

You may extract a single document from such a collection, and
distribute it individually under this License, provided you insert a
copy of this License into the extracted document, and follow this
License in all other respects regarding verbatim copying of that
document.

## 7. AGGREGATION WITH INDEPENDENT WORKS

A compilation of the Document or its derivatives with other separate
and independent documents or works, in or on a volume of a storage or
distribution medium, is called an "aggregate" if the copyright
resulting from the compilation is not used to limit the legal rights
of the compilation's users beyond what the individual works permit.
When the Document is included in an aggregate, this License does not
apply to the other works in the aggregate which are not themselves
derivative works of the Document.

If the Cover Text requirement of section 3 is applicable to these
copies of the Document, then if the Document is less than one half of
the entire aggregate, the Document's Cover Texts may be placed on
covers that bracket the Document within the aggregate, or the
electronic equivalent of covers if the Document is in electronic form.
Otherwise they must appear on printed covers that bracket the whole
aggregate.

## 8. TRANSLATION

Translation is considered a kind of modification, so you may
distribute translations of the Document under the terms of section 4.
Replacing Invariant Sections with translations requires special
permission from their copyright holders, but you may include
translations of some or all Invariant Sections in addition to the
original versions of these Invariant Sections. You may include a
translation of this License, and all the license notices in the
Document, and any Warranty Disclaimers, provided that you also include
the original English version of this License and the original versions
of those notices and disclaimers. In case of a disagreement between
the translation and the original version of this License or a notice
or disclaimer, the original version will prevail.

If a section in the Document is Entitled "Acknowledgements",
"Dedications", or "History", the requirement (section 4) to Preserve
its Title (section 1) will typically require changing the actual
title.

## 9. TERMINATION

You may not copy, modify, sublicense, or distribute the Document
except as expressly provided under this License. Any attempt otherwise
to copy, modify, sublicense, or distribute it is void, and will
automatically terminate your rights under this License.

However, if you cease all violation of this License, then your license
from a particular copyright holder is reinstated (a) provisionally,
unless and until the copyright holder explicitly and finally
terminates your license, and (b) permanently, if the copyright holder
fails to notify you of the violation by some reasonable means prior to
60 days after the cessation.

Moreover, your license from a particular copyright holder is
reinstated permanently if the copyright holder notifies you of the
violation by some reasonable means, this is the first time you have
received notice of violation of this License (for any work) from that
copyright holder, and you cure the violation prior to 30 days after
your receipt of the notice.

Termination of your rights under this section does not terminate the
licenses of parties who have received copies or rights from you under
this License. If your rights have been terminated and not permanently
reinstated, receipt of a copy of some or all of the same material does
not give you any rights to use it.

## 10. FUTURE REVISIONS OF THIS LICENSE

The Free Software Foundation may publish new, revised versions of the
GNU Free Documentation License from time to time. Such new versions
will be similar in spirit to the present version, but may differ in
detail to address new problems or concerns. See
<https://www.gnu.org/licenses/>.

Each version of the License is given a distinguishing version number.
If the Document specifies that a particular numbered version of this
License "or any later version" applies to it, you have the option of
following the terms and conditions either of that specified version or
of any later version that has been published (not as a draft) by the
Free Software Foundation. If the Document does not specify a version
number of this License, you may choose any version ever published (not
as a draft) by the Free Software Foundation. If the Document specifies
that a proxy can decide which future versions of this License can be
used, that proxy's public statement of acceptance of a version
permanently authorizes you to choose that version for the Document.

## 11. RELICENSING

"Massive Multiauthor Collaboration Site" (or "MMC Site") means any
World Wide Web server that publishes copyrightable works and also
provides prominent facilities for anybody to edit those works. A
public wiki that anybody can edit is an example of such a server. A
"Massive Multiauthor Collaboration" (or "MMC") contained in the site
means any set of copyrightable works thus published on the MMC site.

"CC-BY-SA" means the Creative Commons Attribution-Share Alike 3.0
license published by Creative Commons Corporation, a not-for-profit
corporation with a principal place of business in San Francisco,
California, as well as future copyleft versions of that license
published by that same organization.

"Incorporate" means to publish or republish a Document, in whole or in
part, as part of another Document.

An MMC is "eligible for relicensing" if it is licensed under this
License, and if all works that were first published under this License
somewhere other than this MMC, and subsequently incorporated in whole
or in part into the MMC, (1) had no cover texts or invariant sections,
and (2) were thus incorporated prior to November 1, 2008.

The operator of an MMC Site may republish an MMC contained in the site
under CC-BY-SA on the same site at any time before August 1, 2009,
provided the MMC is eligible for relicensing.

## ADDENDUM: How to use this License for your documents

To use this License in a document you have written, include a copy of
the License in the document and put the following copyright and
license notices just after the title page:

        Copyright (C)  YEAR  YOUR NAME.
        Permission is granted to copy, distribute and/or modify this document
        under the terms of the GNU Free Documentation License, Version 1.3
        or any later version published by the Free Software Foundation;
        with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
        A copy of the license is included in the section entitled "GNU
        Free Documentation License".

If you have Invariant Sections, Front-Cover Texts and Back-Cover
Texts, replace the "with â€¦ Texts." line with this:

        with the Invariant Sections being LIST THEIR TITLES, with the
        Front-Cover Texts being LIST, and with the Back-Cover Texts being LIST.

If you have Invariant Sections without Cover Texts, or some other
combination of the three, merge those two alternatives to suit the
situation.

If your document contains nontrivial examples of program code, we
recommend releasing these examples in parallel under your choice of
free software license, such as the GNU General Public License, to
permit their use in free software.